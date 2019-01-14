package com.github.drsmugleaf.mechwarrioronline;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 11/01/2019
 */
public enum Factions {

    CLAN("Clan"),
    INNER_SPHERE("Inner Sphere"),
    NONE("None");

    @NotNull
    private final String NAME;

    Factions(@NotNull String name) {
        NAME = name;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

}
