package com.github.drsmugleaf.pokemon.moves;

import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 16/10/2017.
 */
public enum TrainerMove implements IMoves {

    SWITCH("Switch");

    public final String NAME;

    TrainerMove(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
