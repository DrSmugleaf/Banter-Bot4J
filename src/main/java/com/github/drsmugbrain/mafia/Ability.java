package com.github.drsmugbrain.mafia;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 30/08/2017.
 */
public class Ability {

    private final Abilities ABILITY;
    private int usesLeft;

    public Ability(@Nonnull Abilities ability) {
        this.ABILITY = ability;
    }

    @Nonnull
    protected Abilities getBaseAbility() {
        return this.ABILITY;
    }

    public int getUsesLeft() {
        return this.usesLeft;
    }

    protected void setUsesLeft(int amount) {
        this.usesLeft = amount;
    }

}
