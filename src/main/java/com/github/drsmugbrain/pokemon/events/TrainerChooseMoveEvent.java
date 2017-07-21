package com.github.drsmugbrain.pokemon.events;

import com.github.drsmugbrain.pokemon.Move;
import com.github.drsmugbrain.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerChooseMoveEvent extends TrainerChoosePokemonEvent {

    private final Move MOVE;

    public TrainerChooseMoveEvent(Pokemon pokemon, Move move) {
        super(pokemon);
        this.MOVE = move;
    }

    public Move getMove() {
        return this.MOVE;
    }

}
