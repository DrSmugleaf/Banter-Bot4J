package com.github.drsmugleaf.pokemon.events.pokemon;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class PokemonDeathEvent extends PokemonEvent {

    public PokemonDeathEvent(Pokemon pokemon) {
        super(pokemon);
    }

}
