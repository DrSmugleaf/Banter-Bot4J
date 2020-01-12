package com.github.drsmugleaf.tak.bot.neural.deeplearning;

import com.github.drsmugleaf.tak.board.IPreset;
import com.github.drsmugleaf.tak.board.Preset;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.learning.config.Sgd;

import java.util.logging.Logger;

/**
 * Created by DrSmugleaf on 09/01/2020
 */
public class TakData {

    private static MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .weightInit(WeightInit.XAVIER)
            .activation(Activation.RELU)
            .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
            .updater(new Sgd(0.05))
            .list()
            .layer(0, new DenseLayer.Builder().nIn(getInputs()).nOut(250).build())
            .layer(0, new DenseLayer.Builder().nIn(250).nOut(250).build())
            .layer(0, new DenseLayer.Builder().nIn(250).nOut(getOutputs()).build())
            .backpropType(BackpropType.Standard)
            .build();

    public static void main(String[] args) {
        GameSimulator simulator = new GameSimulator();
        GameResults results = simulator.simulate(500);
        DataSet trainingData = results.getTrainDataSet();
        DataSet testData = results.getTestDataSet();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(10));
        model.fit(trainingData);

        Evaluation evaluation = new Evaluation(getPreset().getAllActions().size());
        INDArray output = model.output(testData.getFeatures());
        evaluation.eval(testData.getLabels(), output);
        Logger.getAnonymousLogger().info(evaluation.stats());
    }

    public static IPreset getPreset() {
        return Preset.getDefault();
    }

    public static int getInputs() {
        IPreset preset = getPreset();
        return preset.getSize() * preset.getSize() * (1 + preset.getStones() * 2);
    }

    public static int getOutputs() {
        return Preset.getDefault().getAllActions().size();
    }

}
