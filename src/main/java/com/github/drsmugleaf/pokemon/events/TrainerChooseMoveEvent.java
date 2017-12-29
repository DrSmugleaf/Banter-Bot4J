package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerChooseMoveEvent extends TrainerChoosePokemonEvent {

    private final Move MOVE;

    public TrainerChooseMoveEvent(@Nonnull Pokemon pokemon, @Nonnull Move move) {
        super(pokemon);
        this.MOVE = move;
    }

    @Nonnull
    public Move getMove() {
        return this.MOVE;
    }

}
