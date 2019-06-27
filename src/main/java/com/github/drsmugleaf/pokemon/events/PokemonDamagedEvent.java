package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public class PokemonDamagedEvent extends PokemonEvent {

    public final int DAMAGE;

    public PokemonDamagedEvent(Pokemon defender, int damage) {
        super(defender);
        DAMAGE = damage;
    }

    public int getDamage() {
        return DAMAGE;
    }

}
