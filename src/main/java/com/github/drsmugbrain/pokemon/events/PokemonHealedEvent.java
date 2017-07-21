package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class PokemonHealedEvent extends PokemonDamagedEvent {

    public PokemonHealedEvent(Pokemon defender, int heal) {
        super(defender, -heal);
    }

    public int getHeal() {
        return -super.getDamage();
    }

}
