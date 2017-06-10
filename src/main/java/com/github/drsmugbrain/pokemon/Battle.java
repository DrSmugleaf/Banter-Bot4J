package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public class Battle {

    private final Trainer TRAINERS[];
    private Set<Pokemon> turnOrder = new LinkedHashSet<>();

    public Battle(@Nonnull Trainer... trainers) {
        this.TRAINERS = trainers;
    }

    public Pokemon getFirstPokemon() {
        return turnOrder.iterator().next();
    }

    public Pokemon getLastPokemon() {
        return (Pokemon) turnOrder.toArray()[turnOrder.size() - 1];
    }

}
