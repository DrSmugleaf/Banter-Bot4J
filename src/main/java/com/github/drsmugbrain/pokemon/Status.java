package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 08/06/2017.
 */
public enum Status {

    BURN("Burn"),
    FREEZE("Freeze"),
    PARALYSIS("Paralysis"),
    POISON("Poison"),
    SLEEP("Sleep"),
    ATTRACT("Attract"),
    CONFUSION("Confusion"),
    CURSE("Curse");

    private String NAME;

    Status(@Nonnull String name) {
        this.NAME = name;
    }

    public String getName() {
        return this.NAME;
    }

}
