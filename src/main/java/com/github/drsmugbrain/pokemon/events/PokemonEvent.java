package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public abstract class PokemonEvent extends Event {

    private final Pokemon POKEMON;

    public PokemonEvent(Pokemon pokemon) {
        super(pokemon.getBattle());
        this.POKEMON = pokemon;
    }

    public Pokemon getPokemon() {
        return this.POKEMON;
    }

}
