package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 12/01/2019
 */
public enum Sections {

    CENTER_TORSO("Center", true, 2),
    HEAD("Head", true, 1),
    LEFT_ARM("Left Arm", true, 12),
    LEFT_LEG("Left Leg", false, 6),
    LEFT_TORSO("Left Torso", true, 12),
    RIGHT_ARM("Right Arm", true, 12),
    RIGHT_LEG("Right Leg", false, 6),
    RIGHT_TORSO("Right Torso", true, 12);

    @NotNull
    private final String NAME;

    private final boolean FITTABLE;
    private final int SLOTS;

    Sections(@NotNull String name, boolean fittable, int slots) {
        NAME = name;
        FITTABLE = fittable;
        SLOTS = slots;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

    public boolean isFittable() {
        return FITTABLE;
    }

    public int getSlots() {
        return SLOTS;
    }

}
