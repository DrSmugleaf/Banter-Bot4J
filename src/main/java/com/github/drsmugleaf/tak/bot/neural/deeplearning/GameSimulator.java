package com.github.drsmugleaf.tak.bot.neural.deeplearning;

import com.github.drsmugleaf.tak.board.layout.IPreset;
import com.github.drsmugleaf.tak.board.layout.Preset;
import com.github.drsmugleaf.tak.bot.neural.reinforcementlearning.INeuralGame;
import com.github.drsmugleaf.tak.bot.neural.reinforcementlearning.NeuralBoard;
import com.github.drsmugleaf.tak.bot.neural.reinforcementlearning.NeuralGame;
import com.github.drsmugleaf.tak.bot.random.RandomAllBot;
import com.github.drsmugleaf.tak.pieces.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by DrSmugleaf on 12/01/2020
 */
public class GameSimulator {

    private final int MAX_TURNS;
    private final IPreset PRESET;
    private final double TRAIN_PERCENTAGE;

    public GameSimulator(int maxTurns, IPreset preset, double trainPercentage) {
        MAX_TURNS = maxTurns;
        PRESET = preset;
        TRAIN_PERCENTAGE = trainPercentage;
    }

    public GameSimulator(double trainPercentage) {
        this(400, Preset.getDefault(), trainPercentage);
    }

    public GameSimulator() {
        this(0.9);
    }

    public int getMaxTurns() {
        return MAX_TURNS;
    }

    public IPreset getPreset() {
        return PRESET;
    }

    public double getTrainPercentage() {
        return TRAIN_PERCENTAGE;
    }

    public GameResults simulate(int games) {
        List<double[][][][]> featuresList = new ArrayList<>();
        List<double[]> labelsList = new ArrayList<>();
        for (int i = 0; i < games; i++) {
            INeuralGame game = new NeuralGame(
                    new NeuralBoard(Preset.getDefault()),
                    "Player 1",
                    "Player 2",
                    RandomAllBot::from,
                    RandomAllBot::from
            );

            double[][][][] tempFeatures = new double[MAX_TURNS][][][];
            double[] tempLabels = new double[MAX_TURNS];
            for (int turn = 0; turn < MAX_TURNS; turn++) {
                if (game.isActive()) {
                    game.nextTurn();
                    tempFeatures[turn] = game.getBoard().toDoubleArray();

                    double label;
                    if (game.getWinner() == game.getPlayer(Color.BLACK)) {
                        label = 1;
                    } else if (game.getWinner() == game.getPlayer(Color.WHITE)) {
                        label = 0;
                    } else {
                        label = 0.5;
                    }
                    tempLabels[turn] = label;
                } else {
                    tempFeatures[turn] = new double[5][5][43];
                    tempLabels[turn] = 0.5;
                }
            }

            for (int j = 0; game.isActive(); j++) {
                if (j >= MAX_TURNS) {
                    Logger.getAnonymousLogger().info("Number of turns reached maximum of " + MAX_TURNS);
                    break;
                }

            }

            featuresList.add(tempFeatures);
            labelsList.add(tempLabels);
        }

        double[][][][][] featuresArray = featuresList.toArray(new double[][][][][]{});
        double[][] labelsArray = labelsList.toArray(new double[][]{});

        Features features = new Features(featuresArray, games, getMaxTurns(), getPreset().getSize(), getPreset().getSize(), 43, getTrainPercentage());
        Labels labels = new Labels(labelsArray, games, getMaxTurns(), getTrainPercentage());

        return new GameResults(features, labels, games, getMaxTurns(), getPreset());
    }

}
