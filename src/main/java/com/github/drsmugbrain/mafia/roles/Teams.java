package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 23/08/2017.
 */
public enum Teams {

    NONE("None"),
    TOWN("Town"),
    MAFIA("Mafia"),
    TRIAD("Triad"),
    NEUTRAL("Neutral");

    private final String NAME;

    Teams(@Nonnull String name) {
        this.NAME = name;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

}
