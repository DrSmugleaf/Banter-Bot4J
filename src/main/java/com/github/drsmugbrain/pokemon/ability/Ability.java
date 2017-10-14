package com.github.drsmugbrain.pokemon.ability;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 15/10/2017.
 */
public class Ability {

    @Nonnull
    private final Abilities ABILITY;

    public Ability(@Nonnull Abilities ability) {
        ABILITY = ability;
    }

    @Nonnull
    public String getName() {
        return ABILITY.getName();
    }

    @Nonnull
    public Abilities get() {
        return ABILITY;
    }

}
