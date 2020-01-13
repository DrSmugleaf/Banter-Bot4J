package com.github.drsmugleaf.tak.bot.neural.reinforcementlearning;

import com.github.drsmugleaf.env.Keys;
import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.board.layout.IPreset;
import com.github.drsmugleaf.tak.board.layout.Preset;
import com.github.drsmugleaf.tak.bot.random.RandomFlatBot;
import com.github.drsmugleaf.tak.player.IPlayer;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.learning.Learning;
import org.deeplearning4j.rl4j.learning.sync.Transition;
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
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by DrSmugleaf on 30/08/2019
 */
public class TakMDP implements MDP<INeuralBoard, Integer, DiscreteSpace> {

    private static DQNFactoryStdDense.Configuration NET = DQNFactoryStdDense
            .Configuration
            .builder()
            .l2(0.001)
            .updater(new Adam(0.0005))
            .numHiddenNodes(800)
            .numLayer(3)
            .build();
    private static QLearning.QLConfiguration QL = new QLearning.QLConfiguration(
            42,    //Random seed
            200,    //Max step By epoch
            15000, //Max step
            150000, //Max size of experience replay
            32,     //size of batches
            500,    //target update (hard)
            10,     //num step noop warmup
            0.01,   //reward scaling
            0.99,   //gamma
            1.0,    //td-error clipping
            0.1f,   //min epsilon
            1000,   //num step for eps greedy anneal
            true    //double DQN
    );
    private static DQNPolicy<INeuralBoard> POLICY;

    private final INeuralGame GAME;
    private final ObservationSpace<INeuralBoard> OBSERVATION_SPACE;
    private final TakSpace ACTION_SPACE;

    public TakMDP(IPreset preset) {
        GAME = new NeuralGame(new NeuralBoard(preset), "Neural Bot 1", "Neural Bot 2", NeuralBot::from, RandomFlatBot::from);
        OBSERVATION_SPACE = new ArrayObservationSpace<>(new int[]{preset.getSize() * preset.getSize() * (preset.getMaximumStackSize())});
        ACTION_SPACE = new TakSpace(GAME);
    }

    public static void main(String[] args) {
        String directory = Keys.TAK_POLICY_DIRECTORY.VALUE;
        new File(directory).mkdirs();
        DataManager manager;
        try {
            manager = new DataManager(true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        TakMDP mdp = new TakMDP(Preset.getDefault());
        QLearningDiscreteDense<INeuralBoard> dql = new QLearningDiscreteDense<>(mdp, NET, QL, manager);
        dql.train();
        POLICY = dql.getPolicy();
        try {
            dql.getPolicy().save(directory + "/pol1");
        } catch (IOException e) {
            throw new UncheckedIOException("Error saving policy", e);
        }

        mdp.close();


        TakMDP mdp2 = new TakMDP(Preset.getDefault());
        DQNPolicy<INeuralBoard> pol2;
        try {
            pol2 = DQNPolicy.load(directory + "/pol1");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        mdp.GAME.reset();
        System.out.println(pol2.getNeuralNet().isRecurrent());
        double[][] input = new double[][]{mdp.getGame().getBoard().toArray()};

        pol2.getNeuralNet().reset();
        Learning.InitMdp<INeuralBoard> initMdp = Learning.initMdp(mdp, null);
        INeuralBoard obs = initMdp.getLastObs();
        INDArray lInput = Learning.getInput(mdp, obs);
        INDArray[] history = new INDArray[] {lInput};
        INDArray hstack = Transition.concat(history);
        hstack = hstack.reshape(Learning.makeShape(1, ArrayUtil.toInts(hstack.shape())));
        INDArray output = pol2.getNeuralNet().output(Nd4j.create(input));

        double rewards = 0;
        int iterations = 1000;
        for (int i = 0; i < iterations; i++) {
            mdp2.reset();
            double reward = pol2.play(mdp2);
            rewards += reward;
            Logger.getAnonymousLogger().info("Reward: " + reward);
        }

        Logger.getAnonymousLogger().info("Average:" + rewards / iterations);
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
    public StepReply<INeuralBoard> step(Integer actionIndex) {
        IPlayer nextPlayer = GAME.getNextPlayer();
        if (!(nextPlayer instanceof NeuralBot)) {
            throw new IllegalStateException();
        }

        INeuralBoard board = GAME.getBoard();
        List<IAction> actions = board.getPreset().getAllActions();
        IAction action = actions.get(actionIndex);
        if (actionIndex.equals(ACTION_SPACE.noOp()) || actionIndex >= actions.size() || !action.canExecute(nextPlayer)) {
            return new StepReply<>(board, Integer.MIN_VALUE, isDone(), new JSONObject("{}"));
        }

        nextPlayer.setNextAction(action);
        GAME.nextTurn();

        if (!isDone()) {
            GAME.nextTurn();
        }

        double reward = 0.5;
        if (getGame().getWinner() != null) {
            reward = getGame().getWinner().getColor() == nextPlayer.getColor() ? 1 : 0;
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
