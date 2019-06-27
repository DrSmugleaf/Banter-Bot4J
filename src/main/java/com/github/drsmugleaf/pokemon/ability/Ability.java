package com.github.drsmugleaf.pokemon.ability;

/**
 * Created by DrSmugleaf on 15/10/2017.
 */
public class Ability {

    private final Abilities ABILITY;
    private boolean suppressed = false;

    public Ability(Abilities ability) {
        ABILITY = ability;
    }

    public String getName() {
        return ABILITY.getName();
    }

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
