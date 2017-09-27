package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 26/09/2017.
 */
public enum Setup {

    SINGLE_BATTLE("Single Battle"),
    DOUBLE_BATTLE("Double Battle"),
    TRIPLE_BATTLE("Triple Battle");

    private final String NAME;

    Setup(@Nonnull String name) {
        this.NAME = name;
    }

    public String getName() {
        return this.NAME;
    }

}
