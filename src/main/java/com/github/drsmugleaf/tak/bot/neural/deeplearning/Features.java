package com.github.drsmugleaf.tak.bot.neural.deeplearning;

/**
 * Created by DrSmugleaf on 12/01/2020
 */
public class Features {

    private final double[][][][][] ARRAY;
    private final int L1;
    private final int L2;
    private final int L3;
    private final int L4;
    private final int L5;
    private final double TRAIN_PERCENTAGE;

    public Features(double[][][][][] array, int l1, int l2, int l3, int l4, int l5, double trainPercentage) {
        ARRAY = array;
        L1 = l1;
        L2 = l2;
        L3 = l3;
        L4 = l4;
        L5 = l5;
        TRAIN_PERCENTAGE = trainPercentage;
    }

    public double[][][][][] getArray() {
        return ARRAY;
    }

    public int getL1() {
        return L1;
    }

    public int getL2() {
        return L2;
    }

    public int getL3() {
        return L3;
    }

    public int getL4() {
        return L4;
    }

    public int getL5() {
        return L5;
    }

    public double getTrainPercentage() {
        return TRAIN_PERCENTAGE;
    }

    public int getTrainSize() {
        return (int) (getArray().length * getTrainPercentage());
    }

    public double[][][][][] getTrain() {
        double[][][][][] train = new double[getTrainSize()][getL2()][getL3()][getL4()][getL5()];
        System.arraycopy(ARRAY, 0, train, 0, train.length);
        return train;
    }

    public int getTestSize() {
        return getL1() - getTrainSize();
    }

    public double[][][][][] getTest() {
        double[][][][][] test = new double[getTestSize()][getL2()][getL3()][getL4()][getL5()];
        System.arraycopy(ARRAY, getTrainSize(), test, 0, test.length);
        return test;
    }

}
