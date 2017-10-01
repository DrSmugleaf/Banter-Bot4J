package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/10/2017.
 */
public enum Tag {

    BERRY_USED("Berry Used");

    private final String NAME;

    Tag(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
