package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 30/12/2018
 */
public class Column {

    @NotNull
    protected final Square[] SQUARES;

    Column(int height) {
        SQUARES = new Square[height];
    }

    @NotNull
    public Square[] getSquares() {
        return SQUARES;
    }

    protected void setSquare(int i, @NotNull Square square) {
        SQUARES[i] = square;
    }

    public boolean canPlace(@NotNull Type type, int row) {
        return row < SQUARES.length && SQUARES[row].canPlace(type);
    }

    @NotNull
    public Square place(@NotNull Piece piece, int row) {
        return SQUARES[row].place(piece);
    }

    @NotNull
    public Square remove(@NotNull Piece piece, int row) {
        return SQUARES[row].remove(piece);
    }

    public boolean hasSquare(@NotNull Color color) {
        for (Square square : SQUARES) {
            if (square.getColor() == color) {
                return true;
            }
        }

        return false;
    }

}
