package com.github.drsmugleaf.tak.bot.neural.deeplearning;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
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

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by DrSmugleaf on 12/01/2020
 */
public class GoBot {

    private static final String RESOURCE_PATH = Objects.requireNonNull(GoBot.class.getClassLoader().getResource("go")).getFile();

    public static void main(String[] args) {
        INDArray features = Nd4j.createFromNpyFile(new File(RESOURCE_PATH + "/features_3000.npy"));
        INDArray labels = Nd4j.createFromNpyFile(new File(RESOURCE_PATH + "/labels_3000.npy"));

        DataSet allData = new DataSet(features, labels);
        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.9);
        DataSet trainingData = testAndTrain.getTrain();
        DataSet testData = testAndTrain.getTest();

        int size = 19;
        int featurePlanes = 11;
        int boardSize = 19 * 19;
        int randomSeed = 1337;

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(randomSeed)
                .weightInit(WeightInit.XAVIER)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(Updater.ADAGRAD)
                .list()
                .layer(0, new ConvolutionLayer.Builder(3, 3)
                        .nIn(featurePlanes).stride(1, 1).nOut(50).activation(Activation.RELU).build())
                .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2).stride(1, 1).build())
                .layer(2, new ConvolutionLayer.Builder(3, 3)
                        .stride(1, 1).nOut(20).activation(Activation.RELU).build())
                .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2).stride(1, 1).build())
                .layer(4, new DenseLayer.Builder().activation(Activation.RELU)
                        .nOut(500).build())
                .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nOut(boardSize).activation(Activation.SOFTMAX).build())
                .setInputType(InputType.convolutional(size, size, featurePlanes))
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(10));
        model.fit(trainingData);

        Evaluation eval = new Evaluation(19 * 19);
        INDArray output = model.output(testData.getFeatures());
        eval.eval(testData.getLabels(), output);
        Logger.getAnonymousLogger().info(eval.stats());
    }

}
