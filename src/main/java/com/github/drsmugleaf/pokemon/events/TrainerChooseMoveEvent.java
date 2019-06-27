package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerChooseMoveEvent extends TrainerChoosePokemonEvent {

    public final Move MOVE;

    public TrainerChooseMoveEvent(Pokemon pokemon, Move move) {
        super(pokemon);
        MOVE = move;
    }

    @Contract(pure = true)
    public Move getMove() {
        return MOVE;
    }

}
