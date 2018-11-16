package com.github.drsmugleaf.pokemon.moves;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 24/09/2017.
 */
public enum MoveEffect {

    ENTRY_HAZARD("Entry Hazard");

    @NotNull
    public final String NAME;

    MoveEffect(@NotNull String name) {
        NAME = name;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

}
