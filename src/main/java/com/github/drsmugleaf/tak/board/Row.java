package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Row {

    @NotNull
    private final Square[] SQUARES;

    Row(int width) {
        SQUARES = new Square[width];
    }

    @NotNull
    public Square[] getSquares() {
        return SQUARES.clone();
    }

    public boolean canPlace(@NotNull Type type, int square) {
        return square < SQUARES.length && SQUARES[square].canPlace(type);
    }

    public void place(@NotNull Piece piece, int square) {
        SQUARES[square].place(piece);
    }

}
