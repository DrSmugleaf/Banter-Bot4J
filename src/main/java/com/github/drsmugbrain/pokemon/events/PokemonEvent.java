package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public abstract class PokemonEvent extends Event {

    private final Pokemon POKEMON;

    public PokemonEvent(@Nonnull Pokemon pokemon) {
        super(pokemon.getBattle());
        this.POKEMON = pokemon;
    }

    @Nonnull
    public Pokemon getPokemon() {
        return this.POKEMON;
    }

}
