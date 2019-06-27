package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public abstract class PokemonEvent extends Event {

    public final Pokemon POKEMON;

    public PokemonEvent(Pokemon pokemon) {
        super(pokemon.getBattle());
        POKEMON = pokemon;
    }

    public Pokemon getPokemon() {
        return POKEMON;
    }

}
