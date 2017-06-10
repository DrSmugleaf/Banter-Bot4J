package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public class Trainer {

    private final String NAME;

    public Trainer(@Nonnull String name) {
        this.NAME = name;
    }

    public String getName() {
        return this.NAME;
    }

}
