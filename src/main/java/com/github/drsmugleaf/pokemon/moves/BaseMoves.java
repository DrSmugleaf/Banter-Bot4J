package com.github.drsmugleaf.pokemon.moves;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 16/10/2017.
 */
public class BaseMoves {

    @NotNull
    private final List<String> MOVE_NAMES = new ArrayList<>();

    BaseMoves(@NotNull List<String> moveNames) {
        MOVE_NAMES.addAll(moveNames);
    }

    @NotNull
    public List<BaseMove> get() {
        List<BaseMove> moves = new ArrayList<>();

        for (String moveName : MOVE_NAMES) {
            BaseMove move = BaseMove.getMove(moveName);
            moves.add(move);
        }

        return moves;
    }

    static class Single {

        @NotNull
        private final String MOVE_NAME;

        Single(@NotNull String moveName) {
            MOVE_NAME = moveName;
        }

        @NotNull
        public BaseMove get() {
            return BaseMove.getMove(MOVE_NAME);
        }

    }

}
