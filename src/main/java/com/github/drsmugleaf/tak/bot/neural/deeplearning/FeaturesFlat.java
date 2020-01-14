package com.github.drsmugleaf.tak.bot.neural.deeplearning;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public class FeaturesFlat {

    private final double[][] ARRAY;
    private final int L1;
    private final int L2;

    public FeaturesFlat(double[][] array, int l1, int l2) {
        ARRAY = array;
        L1 = l1;
        L2 = l2;
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

}
