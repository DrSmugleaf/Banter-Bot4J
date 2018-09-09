package com.github.drsmugleaf.pokemon.moves;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 24/09/2017.
 */
public enum MoveEffect {

    ENTRY_HAZARD("Entry Hazard");

    @Nonnull
    public final String NAME;

    MoveEffect(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
