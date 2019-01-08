package com.github.drsmugleaf.chess.board;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 08/01/2019
 */
public class Line {

    @NotNull
    private final Square[] SQUARES = new Square[8];

    protected Line() {
        for (int i = 0; i < SQUARES.length; i++) {
            SQUARES[i] = new Square();
        }
    }

    @NotNull
    public Square[] getSquares() {
        return SQUARES;
    }

}
