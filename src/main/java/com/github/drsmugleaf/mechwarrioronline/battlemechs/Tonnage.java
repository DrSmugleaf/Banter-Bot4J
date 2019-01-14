package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 10/01/2019
 */
public enum Tonnage {

    LIGHT(20, 35),
    MEDIUM(40, 55),
    HEAVY(60, 75),
    ASSAULT(80, 100);

    private final int MINIMUM;
    private final int MAXIMUM;

    Tonnage(int minimum, int maximum) {
        MINIMUM = minimum;
        MAXIMUM = maximum;
    }

    @NotNull
    public static Tonnage getByWeight(int weight) {
        for (Tonnage tonnage : values()) {
            if (weight >= tonnage.getMinimum() && weight <= tonnage.getMaximum()) {
                return tonnage;
            }
        }

        throw new IllegalArgumentException("Invalid weight: " + weight + ". Minimum weight " + LIGHT.getMinimum() + ", maximum weight " + ASSAULT.getMaximum());
    }

    public static int getSmallest() {
        int minimum = Integer.MAX_VALUE;

        for (Tonnage tonnage : values()) {
            int thisMinimum = tonnage.getMinimum();

            if (minimum > thisMinimum) {
                minimum = thisMinimum;
            }
        }

        return minimum;
    }

    public static int getBiggest() {
        int maximum = Integer.MIN_VALUE;

        for (Tonnage tonnage : values()) {
            int thisMaximum = tonnage.getMaximum();

            if (maximum < thisMaximum) {
                maximum = thisMaximum;
            }
        }

        return maximum;
    }

    public int getMinimum() {
        return MINIMUM;
    }

    public int getMaximum() {
        return MAXIMUM;
    }

}
