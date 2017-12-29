package com.github.drsmugleaf.pokemon.ability;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 15/10/2017.
 */
public class Ability {

    @Nonnull
    private final Abilities ABILITY;

    private boolean suppressed = false;

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

    public boolean isSuppressed() {
        return suppressed;
    }

    public void setSuppressed(boolean bool) {
        suppressed = bool;
    }

}
