package com.github.drsmugleaf.pokemon.moves;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 16/10/2017.
 */
public enum TrainerMove implements IMoves {

    SWITCH("Switch");

    @NotNull
    public final String NAME;

    TrainerMove(@NotNull String name) {
        NAME = name;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

}
