package com.github.drsmugleaf.pokemon.moves;

/**
 * Created by DrSmugleaf on 16/10/2017.
 */
public enum TrainerMove implements IMoves {

    SWITCH("Switch");

    public final String NAME;

    TrainerMove(String name) {
        NAME = name;
    }

    public String getName() {
        return NAME;
    }

}
