package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class PokemonDeathEvent extends PokemonEvent {

    public PokemonDeathEvent(@NotNull Pokemon pokemon) {
        super(pokemon);
    }

}
