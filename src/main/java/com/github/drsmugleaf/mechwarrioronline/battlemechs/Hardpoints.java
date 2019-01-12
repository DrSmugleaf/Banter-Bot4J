package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 10/01/2019
 */
public enum Hardpoints {

    AMS("AMS"),
    BALLISTIC("B"),
    ECM("ECM"),
    ENERGY("E"),
    MISSILE("M");

    @NotNull
    private final String ABBREVIATION;

    Hardpoints(@NotNull String abbreviation) {
        ABBREVIATION = abbreviation;
    }

    @NotNull
    public static Hardpoints from(@NotNull String abbreviation) {
        for (Hardpoints hardpoint : values()) {
            if (hardpoint.ABBREVIATION.equals(abbreviation)) {
                return hardpoint;
            }
        }

        throw new IllegalArgumentException("No hardpoint found with abbreviation " + abbreviation);
    }

    @NotNull
    public String getAbbreviation() {
        return ABBREVIATION;
    }

}
