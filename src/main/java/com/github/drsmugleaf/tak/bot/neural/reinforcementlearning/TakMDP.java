package com.github.drsmugleaf.tak.bot.neural.reinforcementlearning;

import com.github.drsmugleaf.env.Keys;
import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.board.ISquare;
import com.github.drsmugleaf.tak.board.Line;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Piece;
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
import org.json.JSONObject;
import org.nd4j.linalg.learning.config.Adam;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

/**
 * Created by DrSmugleaf on 30/08/2019
 */
public class TakMDP implements MDP<INeuralBoard, Integer, DiscreteSpace> {

    private static DQNFactoryStdDense.Configuration NET = DQNFactoryStdDense
            .Configuration
            .builder()
            .l2(0.001)
            .updater(new Adam(0.0005))
            .numHiddenNodes(100)
            .numLayer(3)
            .build();
    private static QLearning.QLConfiguration QL = new QLearning.QLConfiguration(
            42,
            200,
            1000000,
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
    private static DQNPolicy<INeuralBoard> POLICY;
    private final INeuralGame GAME;
    private final ObservationSpace<INeuralBoard> OBSERVATION_SPACE;
    private final TakSpace ACTION_SPACE;

    public TakMDP(Preset preset) {
        GAME = new NeuralGame(new NeuralBoard(preset), "Neural Bot 1", "Neural Bot 2", NeuralBot::from, NeuralBot::from);
        OBSERVATION_SPACE = new ArrayObservationSpace<>(new int[]{preset.getSize() * preset.getSize() * (1 + preset.getStones() * 2)});
        ACTION_SPACE = new TakSpace(GAME, preset);
    }

    public static void main(String[] args) {
        String directory = Keys.TAK_POLICY_DIRECTORY.VALUE;
        new File(directory).mkdirs();

        TakMDP mdp = new TakMDP(Preset.getDefault());
        QLearningDiscreteDense<INeuralBoard> dql = new QLearningDiscreteDense<>(mdp, NET, QL);
        POLICY = dql.getPolicy();
        dql.train();
        try {
            dql.getPolicy().save(directory + "/pol1");
        } catch (IOException e) {
            throw new UncheckedIOException("Error saving policy", e);
        }

        mdp.close();
    }

    public static DQNPolicy<INeuralBoard> getPolicy() {
        return POLICY;
    }

    @Override
    public ObservationSpace<INeuralBoard> getObservationSpace() {
        return OBSERVATION_SPACE;
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return ACTION_SPACE;
    }

    @Override
    public INeuralBoard reset() {
        GAME.reset();
        return GAME.getBoard();
    }

    @Override
    public void close() {
        GAME.end();
    }

    @Override
    public StepReply<INeuralBoard> step(Integer action) {
        Player nextPlayer = GAME.getNextPlayer();
        if (!(nextPlayer instanceof NeuralBot)) {
            throw new IllegalStateException();
        }

        INeuralBoard board = GAME.getBoard();
        List<ICoordinates> coordinates = nextPlayer.getAvailableActions();
        if (action.equals(ACTION_SPACE.noOp()) || action >= coordinates.size()) {
            nextPlayer.surrender();
            return new StepReply<>(board, Integer.MIN_VALUE, isDone(), new JSONObject("{}"));
        }

        ICoordinates coordinate = coordinates.get(action);
        nextPlayer.setNextAction(coordinate);
        GAME.nextTurn();

        if (!isDone()) {
            GAME.nextTurn();
        }

        int reward = 0;
        if (getGame().getWinner() != null) {
            reward = getGame().getWinner().getColor() == nextPlayer.getColor() ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        } else {
            for (Line column : getGame().getBoard().getColumns()) {
                for (ISquare square : column.getSquares()) {
                    Piece topPiece = square.getTopPiece();
                    if (topPiece == null) {
                        continue;
                    }

                    int adjacentScore = getGame().getBoard().getAdjacent(square).getConnections().size() * 10;
                    reward = topPiece.getColor() == nextPlayer.getColor() ?
                            reward + 1 + adjacentScore :
                            reward - 1 - adjacentScore;
                }
            }
        }

        return new StepReply<>(board, reward, isDone(), new JSONObject("{}"));
    }

    @Override
    public boolean isDone() {
        return !GAME.isActive();
    }

    @Override
    public MDP<INeuralBoard, Integer, DiscreteSpace> newInstance() {
        return new TakMDP(GAME.getBoard().getPreset());
    }

    public INeuralGame getGame() {
        return GAME;
    }

}
