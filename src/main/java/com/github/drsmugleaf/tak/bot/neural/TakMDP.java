package com.github.drsmugleaf.tak.bot.neural;

import com.github.drsmugleaf.env.Keys;
import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.bot.random.RandomFlatBot;
import com.github.drsmugleaf.tak.player.Player;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.learning.sync.qlearning.QLearning;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.deeplearning4j.rl4j.space.ArrayObservationSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;
import org.deeplearning4j.rl4j.util.DataManager;
import org.json.JSONObject;
import org.nd4j.linalg.learning.config.Adam;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

/**
 * Created by DrSmugleaf on 30/08/2019
 */
public class TakMDP implements MDP<NeuralBoard, Integer, DiscreteSpace> {

    private static DQNFactoryStdDense.Configuration NET = DQNFactoryStdDense
            .Configuration
            .builder()
            .l2(0.001)
            .updater(new Adam(0.0005))
            .numHiddenNodes(500)
            .numLayer(3)
            .build();
    private static QLearning.QLConfiguration QL = new QLearning.QLConfiguration(
            42,
            200,
            1000,
            150000,
            32,
            500,
            10,
            0.01,
            0.99,
            1.0,
            0.1f,
            1000,
            true
    );
    private final Game<NeuralBoard> GAME;
    private final ObservationSpace<NeuralBoard> OBSERVATION_SPACE;
    private final TakSpace ACTION_SPACE;

    public TakMDP(Preset preset) {
        GAME = new Game<>(new NeuralBoard(preset), "Neural Bot 1", "Neural Bot 2", NeuralBot::from, RandomFlatBot::from);
        OBSERVATION_SPACE = new ArrayObservationSpace<>(new int[]{preset.getSize() * preset.getSize() * (1 + preset.getStones() * 2)});
        ACTION_SPACE = new TakSpace(GAME, preset);
    }

    public static void main(String[] args) {
        String directory = Keys.TAK_POLICY_DIRECTORY.VALUE;
        new File(directory).mkdirs();

        DataManager manager;
        try {
            manager = new DataManager(true);
        } catch (IOException e) {
            throw new UncheckedIOException("Error creating data manager", e);
        }

        TakMDP mdp = new TakMDP(Preset.getDefault());
        QLearningDiscreteDense<NeuralBoard> dql = new QLearningDiscreteDense<>(mdp, NET, QL, manager);
        dql.train();
        DQNPolicy<NeuralBoard> policy = dql.getPolicy();
        try {
            policy.save(directory + "/pol1");
        } catch (IOException e) {
            throw new UncheckedIOException("Error saving policy", e);
        }

        mdp.close();

//        DQNPolicy<NeuralBoard> policy2;
//        try {
//            policy2 = DQNPolicy.load(directory + "/pol1");
//        } catch (IOException e) {
//            throw new UncheckedIOException("Error loading policy", e);
//        }
//
//        int wins = 0;
//        int losses = 0;
//        int ties = 0;
//        double totalRewards = 0;
//        int iterations = 5000;
//        long time = System.nanoTime();
//        TakMDP mdp2 = new TakMDP(Preset.getDefault());
//        for (int i = 0; i < iterations; i++) {
//            mdp2.reset();
//            double reward = policy2.play(mdp2);
//            totalRewards += reward;
//            Player winner = mdp2.getGame().getWinner();
//            if (winner == null) {
//                ties++;
//            } else if (winner.getName().equals("Neural Bot 1")) {
//                wins++;
//            } else {
//                losses++;
//            }
//        }
//
//        System.out.println(System.nanoTime() - time);
//
//        System.out.println("Average reward: " + totalRewards / iterations);
//
//        System.out.println("Wins: " + wins);
//        System.out.println("Losses: " + losses);
//        System.out.println("Ties: " + ties);
//        System.out.println("Win rate: " + wins / (iterations / 100.0) + "%");
    }

    @Override
    public ObservationSpace<NeuralBoard> getObservationSpace() {
        return OBSERVATION_SPACE;
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return ACTION_SPACE;
    }

    @Override
    public NeuralBoard reset() {
        GAME.resetGame();
        return GAME.getBoard();
    }

    @Override
    public void close() {
        GAME.end();
    }

    @Override
    public StepReply<NeuralBoard> step(Integer action) {
        Player nextPlayer = GAME.getNextPlayer();
        NeuralBoard board = GAME.getBoard();
        List<ICoordinates> coordinates = GAME.getNextPlayer().getAvailableActions();
        if (action.equals(ACTION_SPACE.noOp()) || action >= coordinates.size()) {
            return new StepReply<>(board, Integer.MIN_VALUE, isDone(), new JSONObject("{}"));
        }

        ICoordinates coordinate = coordinates.get(action);
        nextPlayer.setNextAction(coordinate);
        getGame().nextTurn();
        getGame().nextTurn();

        int reward = 0;
        if (GAME.getWinner() != null) {
            reward = GAME.getWinner() == nextPlayer ? Integer.MAX_VALUE / 2 : Integer.MIN_VALUE;
        }

        return new StepReply<>(board, reward, isDone(), new JSONObject("{}"));
    }

    @Override
    public boolean isDone() {
        return !GAME.isActive();
    }

    @Override
    public MDP<NeuralBoard, Integer, DiscreteSpace> newInstance() {
        return new TakMDP(GAME.getBoard().getPreset());
    }

    public Game getGame() {
        return GAME;
    }

}
