package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;

/**
 * Created by DrSmugleaf on 02/01/2019
 */
public class Line {

    protected final ISquare[] SQUARES;

    protected Line(int size) {
        SQUARES = new ISquare[size];
    }

    public ISquare[] getSquares() {
        return SQUARES;
    }

    public boolean canMove(int origin, ISquare destination, int pieces) {
        return origin < SQUARES.length && SQUARES[origin].canMove(destination, pieces);
    }

    public ISquare move(int origin, ISquare destination, int pieces, boolean silent) {
        return SQUARES[origin].move(destination, pieces, silent);
    }

    public boolean canPlace(int location) {
        return location < SQUARES.length && SQUARES[location].canPlace();
    }

    public ISquare place(Piece piece, int location, boolean silent) {
        return SQUARES[location].place(piece, silent);
    }

    public ISquare remove(Piece piece, int location, boolean silent) {
        return SQUARES[location].remove(piece, silent);
    }

    protected void setSquare(int i, ISquare square) {
        SQUARES[i] = square;
    }

    public boolean hasSquare(Color color) {
        for (ISquare square : SQUARES) {
            if (square.getColor() == color) {
                return true;
            }
        }

        return false;
    }

    public int countFlat(Color color) {
        int amount = 0;

        for (ISquare square : SQUARES) {
            Piece topPiece = square.getTopPiece();
            if (topPiece != null && topPiece.getColor() == color && topPiece.getType() == Type.FLAT_STONE) {
                amount++;
            }
        }

        return amount;
    }

    public void reset() {
        for (ISquare square : SQUARES) {
            square.reset();
        }
    }

}
