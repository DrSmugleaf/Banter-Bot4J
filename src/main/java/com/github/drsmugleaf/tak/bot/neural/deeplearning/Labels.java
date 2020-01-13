package com.github.drsmugleaf.tak.bot.neural.deeplearning;

import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

/**
 * Created by DrSmugleaf on 12/01/2020
 */
public class Labels {

    private final double[][] ARRAY;
    private final int L1;
    private final int L2;
    private final double TRAIN_PERCENTAGE;

    public Labels(double[][] array, int l1, int l2, double trainPercentage) {
        ARRAY = array;
        L1 = l1;
        L2 = l2;
        TRAIN_PERCENTAGE = trainPercentage;
    }

    public double[][] getArray() {
        return ARRAY;
    }

    public int getL1() {
        return L1;
    }

    public int getL2() {
        return L2;
    }

    public double getTrainPercentage() {
        return TRAIN_PERCENTAGE;
    }

    public int getTrainSize() {
        return (int) (getArray().length * getTrainPercentage());
    }

    public double[][] getTrain() {
        double[][] train = new double[getTrainSize()][getL2()];
        System.arraycopy(ARRAY, 0, train, 0, train.length);
        return train;
    }

    public int getTestSize() {
        return getL1() - getTrainSize();
    }

    public double[][] getTest() {
        double[][] test = new double[getTestSize()][getL2()];
        System.arraycopy(ARRAY, getTrainSize(), test, 0, test.length);
        return test;
    }

    public File toNpy() {
        File file = new File("labels.npy");
        try {
            Nd4j.writeAsNumpy(Nd4j.create(getArray()), file);
        } catch (IOException e) {
            throw new IllegalStateException("Error writing file as .npy", e);
        }

        return file;
    }

}
