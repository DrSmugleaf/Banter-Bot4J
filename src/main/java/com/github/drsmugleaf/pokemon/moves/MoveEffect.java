package com.github.drsmugleaf.pokemon.moves;

import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 24/09/2017.
 */
public enum MoveEffect {

    ENTRY_HAZARD("Entry Hazard");

    public final String NAME;

    MoveEffect(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

}
