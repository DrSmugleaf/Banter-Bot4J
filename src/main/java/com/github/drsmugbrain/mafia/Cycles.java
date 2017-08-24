package com.github.drsmugbrain.mafia;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 24/08/2017.
 */
public enum Cycles {

    DAY("Day Cycle"),
    LYNCH("Lynch Cycle"),
    NIGHT("Night Cycle");

    private final String NAME;

    Cycles(@Nonnull String name) {
        this.NAME = name;
    }

    public String getName() {
        return this.NAME;
    }

}
