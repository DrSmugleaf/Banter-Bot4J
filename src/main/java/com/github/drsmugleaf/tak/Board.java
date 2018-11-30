package com.github.drsmugleaf.tak;

import com.github.drsmugleaf.tak.pieces.Piece;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Board {

    @NotNull
    private final Piece[][] PIECES;

    private Board(int height, int width) {
        PIECES = new Piece[height][width];
    }

    @NotNull
    public Board getDefault() {
        return new Board(5, 5);
    }

}
