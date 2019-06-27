package com.github.drsmugleaf.pokemon.battle;

import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 26/09/2017.
 */
public enum Variation {

    SINGLE_BATTLE("Single Battle"),
    DOUBLE_BATTLE("Double Battle"),
    TRIPLE_BATTLE("Triple Battle");

    private final String NAME;

    Variation(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

}
