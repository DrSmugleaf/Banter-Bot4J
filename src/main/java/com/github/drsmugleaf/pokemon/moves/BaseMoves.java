package com.github.drsmugleaf.pokemon.moves;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 16/10/2017.
 */
public class BaseMoves {

    @Nonnull
    private final List<String> MOVE_NAMES = new ArrayList<>();

    BaseMoves(@Nonnull List<String> moveNames) {
        MOVE_NAMES.addAll(moveNames);
    }

    @Nonnull
    public List<BaseMove> get() {
        List<BaseMove> moves = new ArrayList<>();

        for (String moveName : MOVE_NAMES) {
            BaseMove move = BaseMove.getMove(moveName);
            moves.add(move);
        }

        return moves;
    }

    static class Single {

        @Nonnull
        private final String MOVE_NAME;

        Single(@Nonnull String moveName) {
            MOVE_NAME = moveName;
        }

        @Nonnull
        public BaseMove get() {
            return BaseMove.getMove(MOVE_NAME);
        }

    }

}
