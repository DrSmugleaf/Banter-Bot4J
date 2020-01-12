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
import org.nd4j.base.Preconditions;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.learning.config.Sgd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.nd4j.linalg.indexing.NDArrayIndex.all;
import static org.nd4j.linalg.indexing.NDArrayIndex.interval;

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

    public static SplitTestAndTrain splitTestAndTrain5D(DataSet data, double fraction) {
        Preconditions.checkArgument(fraction > 0.0 && fraction < 1.0,
                "Train fraction must be > 0.0 and < 1.0 - got %s", fraction);
        int numHoldout = (int) (fraction * data.numExamples());
        if (numHoldout <= 0) {
            numHoldout = 1;
        }

        int numExamples = data.numExamples();
        if (numExamples <= 1)
            throw new IllegalStateException(
                    "Cannot split DataSet with <= 1 rows (data set has " + numExamples + " example)");
        if (numHoldout >= numExamples)
            throw new IllegalArgumentException(
                    "Unable to split on size equal or larger than the number of rows (# numExamples="
                            + numExamples + ", numHoldout=" + numHoldout + ")");
        DataSet first = new DataSet();
        DataSet second = new DataSet();
        INDArray features = data.getFeatures();
        first.setFeatures(features.get(interval(0, numHoldout), all(), all(), all(), all()));
        second.setFeatures(features.get(interval(numHoldout, numExamples), all(), all(), all(), all()));

        INDArray labels = data.getLabels();
        first.setLabels(labels.get(interval(0, numHoldout), all(), all(), all(), all()));
        second.setLabels(labels.get(interval(numHoldout, numExamples), all(), all(), all(), all()));

        INDArray featuresMask = data.getFeaturesMaskArray();
        if (featuresMask != null) {
            first.setFeaturesMaskArray(featuresMask.get(interval(0, numHoldout), all()));
            second.setFeaturesMaskArray(featuresMask.get(interval(numHoldout, numExamples), all()));
        }

        INDArray labelsMask = data.getLabelsMaskArray();
        if (labelsMask != null) {
            first.setLabelsMaskArray(labelsMask.get(interval(0, numHoldout), all()));
            second.setLabelsMaskArray(labelsMask.get(interval(numHoldout, numExamples), all()));
        }

        List<Serializable> exampleMetaData = data.getExampleMetaData();
        if (exampleMetaData != null) {
            List<Serializable> meta1 = new ArrayList<>();
            List<Serializable> meta2 = new ArrayList<>();
            for (int i = 0; i < numHoldout && i < exampleMetaData.size(); i++) {
                meta1.add(exampleMetaData.get(i));
            }
            for (int i = numHoldout; i < numExamples && i < exampleMetaData.size(); i++) {
                meta2.add(exampleMetaData.get(i));
            }
            first.setExampleMetaData(meta1);
            second.setExampleMetaData(meta2);
        }

        return new SplitTestAndTrain(first, second);
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
