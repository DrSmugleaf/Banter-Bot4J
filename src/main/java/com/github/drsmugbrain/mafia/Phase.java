package com.github.drsmugbrain.mafia;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 25/08/2017.
 */
public enum Phase {

    DAY("Day"),
    NIGHT("Night"),
    LYNCH("Lynch");

    private final String NAME;

    Phase(@Nonnull String name) {
        this.NAME = name;
    }

    public String getName() {
        return this.NAME;
    }

}
