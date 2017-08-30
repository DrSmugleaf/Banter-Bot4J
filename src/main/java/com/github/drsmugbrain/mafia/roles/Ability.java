package com.github.drsmugbrain.mafia.roles;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 30/08/2017.
 */
public class Ability {

    private final Abilities ABILITY;
    private int usesLeft;

    Ability(@Nonnull Abilities ability) {
        this.ABILITY = ability;
    }

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
