package com.github.drsmugleaf.deadbydaylight.dennisreep;

import javax.annotation.Nonnull;
import java.awt.Color;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public enum Tiers {

    S("S", 4.50, new Color(60, 0, 60, 127)),
    A("A", 4.00, new Color(0, 60, 0, 127)),
    B("B", 3.00, new Color(0, 60, 60, 127)),
    C("C", 2.25, new Color(60, 60, 0, 127)),
    D("D", 1.50, new Color(60, 0, 0, 127)),
    F("F", 0.00, new Color(0, 0, 0, 127));

    @Nonnull
    public final String NAME;

    public final double THRESHOLD;

    @Nonnull
    public final Color COLOR;

    Tiers(@Nonnull String name, double threshold, @Nonnull Color color) {
        NAME = name;
        THRESHOLD = threshold;
        COLOR = color;
    }

    @Nonnull
    public static Tiers from(double rating) {
        for (Tiers tier : values()) {
            if (rating >= tier.THRESHOLD) {
                return tier;
            }
        }

        return F;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
