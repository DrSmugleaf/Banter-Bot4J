package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 20/07/2017.
 */
public class PokemonDodgeEvent extends PokemonEvent {

    public PokemonDodgeEvent(@NotNull Pokemon pokemon) {
        super(pokemon);
    }

}
