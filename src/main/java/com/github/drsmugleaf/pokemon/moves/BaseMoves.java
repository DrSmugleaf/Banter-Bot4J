package com.github.drsmugleaf.pokemon.moves;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 16/10/2017.
 */
class BaseMoves {

    @Nonnull
    private final String[] MOVE_NAMES;

    BaseMoves() {
        MOVE_NAMES = new String[]{};
    }

    BaseMoves(@Nullable String[] moves) {
        if (moves == null || String.join("", moves).isEmpty()) {
            MOVE_NAMES = new String[]{};
        } else {
            MOVE_NAMES = moves;
        }
    }

    @Nonnull
    public List<BaseMove> get() {
        List<BaseMove> moves = new ArrayList<>();
        for (String name : MOVE_NAMES) {
            moves.add(BaseMove.getMove(name));
        }
        return moves;
    }

    static class Single {

        @Nullable
        private final String MOVE_NAME;

        Single() {
            MOVE_NAME = null;
        }

        Single(@Nullable String move) {
            if (move != null && move.isEmpty()) {
                MOVE_NAME = null;
            } else {
                MOVE_NAME = move;
            }
        }

        @Nullable
        public BaseMove get() {
            if (MOVE_NAME == null) {
                return null;
            }

            return BaseMove.getMove(MOVE_NAME);
        }

    }

}
