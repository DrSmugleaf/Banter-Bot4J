package com.github.drsmugleaf.pokemon.moves;

/**
 * Created by DrSmugleaf on 24/09/2017.
 */
public enum MoveEffect {

    ENTRY_HAZARD("Entry Hazard");

    public final String NAME;

    MoveEffect(String name) {
        NAME = name;
    }

    public String getName() {
        return NAME;
    }

}
