package com.github.drsmugleaf.pokemon.moves;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 16/10/2017.
 */
public enum TrainerMove implements IMoves {

    SWITCH("Switch");

    @Nonnull
    public final String NAME;

    TrainerMove(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
