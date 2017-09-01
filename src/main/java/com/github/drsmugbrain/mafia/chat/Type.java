package com.github.drsmugbrain.mafia.chat;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 01/09/2017.
 */
public enum Type {

    TOWN("Town"),
    MAFIA("Mafia"),
    TRIAD("Triad"),
    PRIVATE("Private");

    private final String NAME;

    Type(@Nonnull String name) {
        this.NAME = name;
    }

    public String getName() {
        return this.NAME;
    }

}
