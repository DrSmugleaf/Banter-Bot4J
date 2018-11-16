package com.github.drsmugleaf.pokemon.battle;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 26/09/2017.
 */
public enum Variation {

    SINGLE_BATTLE("Single Battle"),
    DOUBLE_BATTLE("Double Battle"),
    TRIPLE_BATTLE("Triple Battle");

    @NotNull
    private final String NAME;

    Variation(@NotNull String name) {
        NAME = name;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

}
