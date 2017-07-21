package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Move;
import com.github.drsmugbrain.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 20/07/2017.
 */
public class PokemonMoveMissEvent extends PokemonMoveEvent {

    private final Pokemon TARGET;

    public PokemonMoveMissEvent(Pokemon pokemon, Move move, Pokemon target) {
        super(pokemon, move);
        this.TARGET = target;
    }

    public Pokemon getTarget() {
        return this.TARGET;
    }

}
