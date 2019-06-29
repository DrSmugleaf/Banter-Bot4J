package com.github.drsmugleaf.pokemon.events.pokemon;

import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 17/07/2017.
 */
public class PokemonMoveEvent extends PokemonEvent {

    public final Move MOVE;

    public PokemonMoveEvent(Pokemon pokemon, Move move) {
        super(pokemon);
        MOVE = move;
    }

    @Contract(pure = true)
    public Move getMove() {
        return MOVE;
    }

}
