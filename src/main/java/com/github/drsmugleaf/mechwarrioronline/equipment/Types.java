package com.github.drsmugleaf.mechwarrioronline.equipment;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 11/01/2019
 */
public enum Types {

    BALLISTIC("Ballistic"),
    ENERGY("Energy"),
    MISSILE("Missile"),
    UTILITY("Utility");

    @NotNull
    private final String NAME;

    Types(@NotNull String name) {
        NAME = name;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

}
