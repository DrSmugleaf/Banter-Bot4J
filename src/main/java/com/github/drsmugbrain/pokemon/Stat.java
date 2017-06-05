package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public enum Stat {

    HP("Health"),
    ATTACK("Attack"),
    DEFENSE("Defense"),
    SPEED("Speed"),
    SPECIAL_ATTACK("Special Attack"),
    SPECIAL_DEFENSE("Special Defense"),
    ACCURACY("Accuracy"),
    EVASION("Evasion");

    private final String NAME;

    Stat(@Nonnull String name) {
        this.NAME = name;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

}
