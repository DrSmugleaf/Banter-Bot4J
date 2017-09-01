package com.github.drsmugbrain.mafia;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 31/08/2017.
 */
public enum PlayerStates {

    ON_TRIAL("On Trial"),
    LAST_WORDS("Last Words"),
    DEAD("Dead");

    private final String NAME;

    PlayerStates(@Nonnull String name) {
        this.NAME = name;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

}
