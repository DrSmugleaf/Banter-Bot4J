package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 12/01/2019
 */
public enum Sections {

    CENTER_TORSO("Center"),
    HEAD("Head"),
    LEFT_ARM("Left Arm"),
    LEFT_LEG("Left Leg"),
    LEFT_TORSO("Left Torso"),
    RIGHT_ARM("Right Arm"),
    RIGHT_LEG("Right Leg"),
    RIGHT_TORSO("Right Torso");

    @NotNull
    private final String NAME;

    Sections(@NotNull String name) {
        NAME = name;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

}
