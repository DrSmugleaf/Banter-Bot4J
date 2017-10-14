package com.github.drsmugbrain.pokemon.moves;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 24/09/2017.
 */
public enum MoveEffect {

    ENTRY_HAZARD("Entry Hazard");

    private final String NAME;

    MoveEffect(@Nonnull String name) {
        this.NAME = name;
    }

}
