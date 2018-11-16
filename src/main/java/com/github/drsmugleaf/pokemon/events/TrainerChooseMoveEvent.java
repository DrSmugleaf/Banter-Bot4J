package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 19/07/2017.
 */
public class TrainerChooseMoveEvent extends TrainerChoosePokemonEvent {

    @NotNull
    public final Move MOVE;

    public TrainerChooseMoveEvent(@NotNull Pokemon pokemon, @NotNull Move move) {
        super(pokemon);
        MOVE = move;
    }

    @NotNull
    public Move getMove() {
        return MOVE;
    }

}
