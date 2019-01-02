package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 02/01/2019
 */
public class Line {

    @NotNull
    protected final Square[] SQUARES;

    protected Line(int size) {
        SQUARES = new Square[size];
    }

    @NotNull
    public Square[] getSquares() {
        return SQUARES;
    }

    public boolean canPlace(@NotNull Type type, int location) {
        return location < SQUARES.length && SQUARES[location].canPlace(type);
    }

    @NotNull
    public Square place(@NotNull Piece piece, int location) {
        return SQUARES[location].place(piece);
    }

    @NotNull
    public Square remove(@NotNull Piece piece, int location) {
        return SQUARES[location].remove(piece);
    }

    protected void setSquare(int i, @NotNull Square square) {
        SQUARES[i] = square;
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
