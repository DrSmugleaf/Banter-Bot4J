package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Row {

    @NotNull
    protected final Square[] SQUARES;

    Row(int width) {
        SQUARES = new Square[width];
    }

    @NotNull
    public Square[] getSquares() {
        return SQUARES;
    }

    protected void setSquare(int i, @NotNull Square square) {
        SQUARES[i] = square;
    }

    public boolean canPlace(@NotNull Type type, int column) {
        return column < SQUARES.length && SQUARES[column].canPlace(type);
    }

    @NotNull
    public Square place(@NotNull Piece piece, int column) {
        return SQUARES[column].place(piece);
    }

    @NotNull
    public Square remove(@NotNull Piece piece, int column) {
        return SQUARES[column].remove(piece);
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
