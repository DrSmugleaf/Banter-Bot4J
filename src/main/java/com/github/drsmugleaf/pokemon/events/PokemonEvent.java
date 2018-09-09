package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public abstract class PokemonEvent extends Event {

    @Nonnull
    public final Pokemon POKEMON;

    public PokemonEvent(@Nonnull Pokemon pokemon) {
        super(pokemon.getBattle());
        POKEMON = pokemon;
    }

    @Nonnull
    public Pokemon getPokemon() {
        return POKEMON;
    }

}
