package com.github.drsmugleaf.tak.bot.neural;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.board.Line;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.board.Square;
import com.github.drsmugleaf.tak.bot.Bot;
import com.github.drsmugleaf.tak.bot.random.RandomFlatBot;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
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
import org.jetbrains.annotations.Contract;
import org.json.JSONObject;
import org.nd4j.linalg.learning.config.Adam;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

/**
 * Created by DrSmugleaf on 16/08/2019
 */
public class NeuralBot extends Bot implements MDP<NeuralBoard, Integer, DiscreteSpace> {

    public static QLearning.QLConfiguration QL = new QLearning
            .QLConfiguration(
            42,
            200,
            150000,
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
    public static DQNFactoryStdDense.Configuration NET = DQNFactoryStdDense
            .Configuration
            .builder()
            .l2(0.001)
            .updater(new Adam(0.0005))
            .numHiddenNodes(16)
            .numLayer(3)
            .build();

    private final DiscreteSpace ACTION_SPACE;
    private final ObservationSpace<NeuralBoard> OBSERVATION_SPACE = new ArrayObservationSpace<>(new int[]{550});
    private static int wins = 0;
    private static int other = 0;
    private static int ties = 0;

    protected NeuralBot(String name, NeuralGame game, Color color, Preset preset) {
        super(name, game, color, preset);
        ACTION_SPACE = new TakSpace(game, preset);
    }

    public static void main(String[] args) {
        DataManager manager;
        try {
            manager = new DataManager(true);
        } catch (IOException e) {
            throw new UncheckedIOException("Error creating data manager", e);
        }

        NeuralGame game = new NeuralGame(Preset.getDefault(), "Bot 1", "Bot 2", NeuralBot::from, RandomFlatBot::from);
        NeuralBot mdp = (NeuralBot) game.getPlayer(Color.BLACK);
        QLearningDiscreteDense<NeuralBoard> dql = new QLearningDiscreteDense<>(mdp, NET, QL, manager);
        dql.train();
        DQNPolicy<NeuralBoard> policy = dql.getPolicy();
        try {
            policy.save("C:\\Users\\javie\\tmp\\poll");
        } catch (IOException e) {
            throw new UncheckedIOException("Error saving policy", e);
        }

        mdp.close();

        NeuralGame game2 = new NeuralGame(Preset.getDefault(), "Bot 1", "Bot 2", NeuralBot::from, RandomFlatBot::from);
        NeuralBot mdp2 = (NeuralBot) game2.getPlayer(Color.BLACK);
        DQNPolicy<NeuralBoard> policy2;
        try {
            policy2 = DQNPolicy.load("C:\\Users\\javie\\tmp\\poll");
        } catch (IOException e) {
            throw new UncheckedIOException("Error loading policy", e);
        }

        double totalRewards = 0;
        int iterations = 1000;
        long time = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            mdp2.reset();
            double reward = policy2.play(mdp2);
            totalRewards += reward;
        }

        System.out.println(System.nanoTime() - time);

        System.out.println("Average reward: " + totalRewards / iterations);

        System.out.println("Wins: " + wins);
        System.out.println("Other: " + other);
        System.out.println("Ties: " + ties);
        System.out.println("Win rate: " + wins / (iterations / 100.0) + "%");
    }

    @Contract("_ -> new")
    public static Player from(PlayerInformation<NeuralGame> information) {
        return new NeuralBot(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @Nullable
    @Override
    public ICoordinates getNextAction() {
        return null;
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
        getGame().resetGame();
        return (NeuralBoard) getGame().getBoard();
    }

    @Override
    public void close() {
        surrender();
    }

    @Override
    public StepReply<NeuralBoard> step(Integer action) {
        List<ICoordinates> coordinates = getAvailableActions();
        if (action.equals(ACTION_SPACE.noOp()) || action >= coordinates.size()) {
            surrender();
            return new StepReply<>((NeuralBoard) getGame().getBoard(), Integer.MIN_VALUE, isDone(), new JSONObject("{}"));
        }

        ICoordinates coordinate = coordinates.get(action);
        coordinate.place(this);

        getGame().nextPlayer = getGame().getOtherPlayer(this);
        getGame().nextTurn();

        int reward = 0;
        if (getGame().getWinner() != null) {
            reward = getGame().getWinner().getColor() == getColor() ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        } else {
            for (Line column : getGame().getBoard().getColumns()) {
                for (Square square : column.getSquares()) {
                    Piece topPiece = square.getTopPiece();
                    if (topPiece == null) {
                        continue;
                    }

                    int adjacentScore = getGame().getBoard().getAdjacent(square).getConnections().size() * 10;
                    reward = topPiece.getColor() == getColor() ?
                            reward + 1 + adjacentScore :
                            reward - 1 - adjacentScore;
                }
            }
        }

        return new StepReply<>((NeuralBoard) getGame().getBoard(), reward, isDone(), new JSONObject("{}"));
    }

    @Override
    public boolean isDone() {
        return !getGame().isActive();
    }

    @Override
    public MDP<NeuralBoard, Integer, DiscreteSpace> newInstance() {
        return new NeuralBot(getName(), (NeuralGame) getGame(), getColor(), getGame().getBoard().getPreset());
    }

    @Override
    public void onGameEnd(@Nullable Player winner) {
        if (equals(winner)) {
            wins++;
        } else if (winner != null) {
            other++;
        } else {
            ties++;
        }
    }

}
