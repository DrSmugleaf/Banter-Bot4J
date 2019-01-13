package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 10/01/2019
 */
public enum Hardpoints {

    AMS("AMS", "AMS"),
    BALLISTIC("B", "Ballistic"),
    ECM("ECM", "ECM"),
    ENERGY("E", "Energy"),
    MISSILE("M", "Missile");

    @NotNull
    private final String ABBREVIATION;

    @NotNull
    private final String NAME;

    Hardpoints(@NotNull String abbreviation, @NotNull String name) {
        ABBREVIATION = abbreviation;
        NAME = name;
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

    @NotNull
    public String getName() {
        return NAME;
    }

}
