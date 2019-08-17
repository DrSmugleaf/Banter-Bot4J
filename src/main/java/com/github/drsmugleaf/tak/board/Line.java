package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;

/**
 * Created by DrSmugleaf on 02/01/2019
 */
public class Line {

    protected final Square[] SQUARES;

    protected Line(int size) {
        SQUARES = new Square[size];
    }

    public Square[] getSquares() {
        return SQUARES;
    }

    public boolean canMove(int origin, Square destination, int pieces) {
        return origin < SQUARES.length && SQUARES[origin].canMove(destination, pieces);
    }

    public Square move(int origin, Square destination, int pieces, boolean silent) {
        return SQUARES[origin].move(destination, pieces, silent);
    }

    public boolean canPlace(int location) {
        return location < SQUARES.length && SQUARES[location].canPlace();
    }

    public Square place(Piece piece, int location, boolean silent) {
        return SQUARES[location].place(piece, silent);
    }

    public Square remove(Piece piece, int location, boolean silent) {
        return SQUARES[location].remove(piece, silent);
    }

    protected void setSquare(int i, Square square) {
        SQUARES[i] = square;
    }

    public boolean hasSquare(Color color) {
        for (Square square : SQUARES) {
            if (square.getColor() == color) {
                return true;
            }
        }

        return false;
    }

    public int countFlat(Color color) {
        int amount = 0;

        for (Square square : SQUARES) {
            Piece topPiece = square.getTopPiece();
            if (topPiece != null && topPiece.getColor() == color && topPiece.getType() == Type.FLAT_STONE) {
                amount++;
            }
        }

        return amount;
    }

}
