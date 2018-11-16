package com.github.drsmugleaf.pokemon.ability;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 15/10/2017.
 */
public class Ability {

    @NotNull
    private final Abilities ABILITY;

    private boolean suppressed = false;

    public Ability(@NotNull Abilities ability) {
        ABILITY = ability;
    }

    @NotNull
    public String getName() {
        return ABILITY.NAME;
    }

    @NotNull
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
