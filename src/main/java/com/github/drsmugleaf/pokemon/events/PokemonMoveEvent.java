package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public class PokemonMoveEvent extends PokemonEvent {

    public final Move MOVE;

    public PokemonMoveEvent(Pokemon pokemon, Move move) {
        super(pokemon);
        MOVE = move;
    }

    public Move getMove() {
        return MOVE;
    }

}
