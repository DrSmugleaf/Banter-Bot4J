package com.github.drsmugleaf.pokemon.events;

import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public class PokemonMoveEvent extends PokemonEvent {

    private final Move MOVE;

    public PokemonMoveEvent(@Nonnull Pokemon pokemon, @Nonnull Move move) {
        super(pokemon);
        MOVE = move;
    }

    @Nonnull
    public Move getMove() {
        return this.MOVE;
    }

}
