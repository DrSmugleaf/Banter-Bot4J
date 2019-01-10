package com.github.drsmugleaf.chess.pieces;

/**
 * Created by DrSmugleaf on 08/01/2019
 */
public enum Color {

    BLACK {
        @Override
        public int getForward() {
            return -1;
        }
    },
    WHITE {
        @Override
        public int getForward() {
            return 1;
        }
    };

    public abstract int getForward();

}
