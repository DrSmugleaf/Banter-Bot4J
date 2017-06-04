package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public class Stat {

    private String name;

    public static final Stat HP = new Stat("Health");
    public static final Stat ATTACK = new Stat("Attack");
    public static final Stat DEFENSE = new Stat("Defense");
    public static final Stat SPEED = new Stat("Speed");
    public static final Stat SPECIAL_ATTACK = new Stat("Special Attack");
    public static final Stat SPECIAL_DEFENSE = new Stat("Special Defense");
    public static final Stat ACCURACY = new Stat("Accuracy");
    public static final Stat EVASION = new Stat("Evasion");

    private Stat(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public String getName() {
        return this.name;
    }

}
