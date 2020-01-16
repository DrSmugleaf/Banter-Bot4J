package com.github.drsmugleaf.tak.bot.neural.deeplearning;

import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.layout.IPreset;
import com.github.drsmugleaf.tak.board.layout.Preset;
import com.github.drsmugleaf.tak.bot.random.RandomBot;
import com.github.drsmugleaf.tak.game.Game;
import com.github.drsmugleaf.tak.pieces.Color;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.logging.Logger;

/**
 * Created by DrSmugleaf on 09/01/2020
 */
public class TakData {

    private static MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(42)
            .weightInit(WeightInit.XAVIER)
            .activation(Activation.RELU)
            .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
            .updater(Updater.ADAGRAD)
            .list()
            .layer(0, new ConvolutionLayer.Builder(3, 3)
                    .nIn(getInputs()).stride(1, 1).nOut(50).activation(Activation.RELU).build())
            .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                    .kernelSize(2, 2).stride(1, 1).build())
            .layer(2, new ConvolutionLayer.Builder(3, 3)
                    .stride(1, 1).nOut(20).activation(Activation.RELU).build())
            .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                    .kernelSize(2, 2).stride(1, 1).build())
            .layer(4, new DenseLayer.Builder()
                    .activation(Activation.RELU).nOut(500).build())
            .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                    .nOut(getOutputs()).activation(Activation.SOFTMAX).build())
            .setInputType(InputType.convolutionalFlat(getPreset().getSize(), getPreset().getSize(), getPreset().getMaximumStackSize()))
            .backpropType(BackpropType.Standard)
            .build();

    public static void main(String[] args) {
        GameSimulator simulator = new GameSimulator();
        GameResults results = simulator.simulateFlat(500);
        INDArray features = Nd4j.create(results.getFeatures().getArray());
        INDArray labels = Nd4j.create(results.getLabels().getArray());

        DataSet allData = new DataSet(features, labels);
        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.9);
        DataSet trainingData = testAndTrain.getTrain();
        DataSet testData = testAndTrain.getTest();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(10));
        model.fit(trainingData);

        Evaluation evaluation = new Evaluation(getPreset().getAllActions().size());
        INDArray output = model.output(testData.getFeatures());
        evaluation.eval(testData.getLabels(), output);
        Logger.getAnonymousLogger().info(evaluation.stats());

        Game game;
        game = new Game(new Board(Preset.getDefault()), "Neural Bot", "Random Bot", DLBot.from(model), RandomBot::from);
        int win = 0;
        int loss = 0;
        int tie = 0;
        for (int i = 0; i < 500; i++) {
            while (game.isActive()) {
                game.nextTurn();
            }

            if (game.getWinner() == game.getPlayer(Color.BLACK)) {
                win++;
            } else if (game.getWinner() == game.getPlayer(Color.WHITE)) {
                loss++;
            } else {
                tie++;
            }
        }

        System.out.println("Win: " + win);
        System.out.println("Loss: " + loss);
        System.out.println("Tie: " + tie);
    }

    public static IPreset getPreset() {
        return Preset.getDefault();
    }

    public static int getInputs() {
        IPreset preset = getPreset();
        return preset.getSize() * preset.getSize() * preset.getMaximumStackSize();
    }

    public static int getOutputs() {
        return Preset.getDefault().getAllActions().size();
    }

}
