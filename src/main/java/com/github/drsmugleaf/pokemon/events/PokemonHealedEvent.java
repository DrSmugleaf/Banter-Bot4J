package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class PokemonHealedEvent extends PokemonEvent {

    public final int HEAL;

    public PokemonHealedEvent(Pokemon defender, int heal) {
        super(defender);
        HEAL = heal;
    }

    public int getHeal() {
        return HEAL;
    }

}
