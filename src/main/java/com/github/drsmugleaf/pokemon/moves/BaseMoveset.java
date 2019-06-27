package com.github.drsmugleaf.pokemon.moves;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 16/10/2017.
 */
public class BaseMoveset {

    private final List<String> MOVE_NAMES = new ArrayList<>();

    BaseMoveset(List<String> moveNames) {
        MOVE_NAMES.addAll(moveNames);
    }

    public List<BaseMove> get() {
        List<BaseMove> moves = new ArrayList<>();

        for (String moveName : MOVE_NAMES) {
            BaseMove move = BaseMove.getMove(moveName);
            moves.add(move);
        }

        return moves;
    }

    static class Single {

        private final String MOVE_NAME;

        Single(String moveName) {
            MOVE_NAME = moveName;
        }

        public BaseMove get() {
            return BaseMove.getMove(MOVE_NAME);
        }

    }

}
