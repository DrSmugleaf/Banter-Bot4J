package com.github.drsmugleaf.pokemon.battle;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 26/09/2017.
 */
public enum Variation {

    SINGLE_BATTLE("Single Battle"),
    DOUBLE_BATTLE("Double Battle"),
    TRIPLE_BATTLE("Triple Battle");

    @Nonnull
    private final String NAME;

    Variation(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
