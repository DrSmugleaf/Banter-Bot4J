package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public class PokemonMoveEvent extends PokemonEvent {

    @NotNull
    public final Move MOVE;

    public PokemonMoveEvent(@NotNull Pokemon pokemon, @NotNull Move move) {
        super(pokemon);
        MOVE = move;
    }

    @NotNull
    public Move getMove() {
        return MOVE;
    }

}
