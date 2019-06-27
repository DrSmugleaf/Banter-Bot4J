package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerChooseMoveEvent extends TrainerChoosePokemonEvent {

    public final Move MOVE;

    public TrainerChooseMoveEvent(Pokemon pokemon, Move move) {
        super(pokemon);
        MOVE = move;
    }

    public Move getMove() {
        return MOVE;
    }

}
