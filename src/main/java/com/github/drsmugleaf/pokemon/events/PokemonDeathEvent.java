package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class PokemonDeathEvent extends PokemonEvent {

    public PokemonDeathEvent(@Nonnull Pokemon pokemon) {
        super(pokemon);
    }

}
