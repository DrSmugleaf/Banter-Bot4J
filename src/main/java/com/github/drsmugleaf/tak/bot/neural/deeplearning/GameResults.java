package com.github.drsmugleaf.tak.bot.neural.deeplearning;

import com.github.drsmugleaf.tak.board.IPreset;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.util.ArrayUtil;

/**
 * Created by DrSmugleaf on 12/01/2020
 */
public class GameResults {

    private final Features FEATURES;
    private final Labels LABELS;
    private final int ITERATIONS;
    private final int MAX_TURNS;
    private final IPreset PRESET;

    public GameResults(Features features, Labels labels, int iterations, int maxTurns, IPreset preset) {
        FEATURES = features;
        LABELS = labels;
        ITERATIONS = iterations;
        MAX_TURNS = maxTurns;
        PRESET = preset;
    }

    public Features getFeatures() {
        return FEATURES;
    }

    public INDArray getFeaturesINDTrain() {
        double[] featuresFlat = ArrayUtil.flattenDoubleArray(getFeatures().getTrain());
        int[] featuresShape = new int[]{getFeatures().getTrainSize(), getMaxTurns(), getPreset().getSize(), getPreset().getSize(), 43};
        return Nd4j.create(featuresFlat, featuresShape, 'c');
    }

    public INDArray getFeaturesINDTest() {
        double[] featuresFlat = ArrayUtil.flattenDoubleArray(getFeatures().getTest());
        int[] featuresShape = new int[]{getFeatures().getTestSize(), getMaxTurns(), getPreset().getSize(), getPreset().getSize(), 43};
        return Nd4j.create(featuresFlat, featuresShape, 'c');
    }

    public Labels getLabels() {
        return LABELS;
    }

    public INDArray getLabelsINDTrain() {
        double[] labelsFlat = ArrayUtil.flattenDoubleArray(getLabels().getTrain());
        int[] labelsShape = new int[]{getLabels().getTrainSize(), getMaxTurns()};
        return Nd4j.create(labelsFlat, labelsShape, 'c');
    }

    public INDArray getLabelsINDTest() {
        double[] labelsFlat = ArrayUtil.flattenDoubleArray(getLabels().getTest());
        int[] labelsShape = new int[]{getLabels().getTestSize(), getMaxTurns()};
        return Nd4j.create(labelsFlat, labelsShape, 'c');
    }

    public int getIterations() {
        return ITERATIONS;
    }

    public int getMaxTurns() {
        return MAX_TURNS;
    }

    public IPreset getPreset() {
        return PRESET;
    }

    public DataSet getTrainDataSet() {
        return new DataSet(getFeaturesINDTrain(), getLabelsINDTrain());
    }

    public DataSet getTestDataSet() {
        return new DataSet(getFeaturesINDTest(), getLabelsINDTest());
    }

}
