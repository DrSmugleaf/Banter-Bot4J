package com.github.drsmugleaf.tak.board.layout;

import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IPiece;
import com.github.drsmugleaf.tak.pieces.Type;

/**
 * Created by DrSmugleaf on 02/01/2019
 */
public class Line implements ILine {

    private final ISquare[] SQUARES;

    protected Line(int size) {
        SQUARES = new ISquare[size];
    }

    @Override
    public ISquare[] getSquares() {
        return SQUARES;
    }

    @Override
    public ISquare move(int origin, int amount, ISquare destination, boolean silent) {
        return getSquares()[origin].move(amount, destination, silent);
    }

    @Override
    public boolean canPlace(int index) {
        return index < getSquares().length && getSquares()[index].canPlace();
    }

    @Override
    public ISquare place(IPiece piece, int location, boolean silent) {
        return getSquares()[location].place(piece, silent);
    }

    @Override
    public ISquare remove(IPiece piece, int location, boolean silent) {
        return getSquares()[location].remove(piece, silent);
    }

    @Override
    public void setSquare(int index, ISquare square) {
        getSquares()[index] = square;
    }

    @Override
    public boolean hasSquare(IColor color) {
        for (ISquare square : getSquares()) {
            if (square.getColor() == color) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int countFlat(IColor color) {
        int amount = 0;

        for (ISquare square : getSquares()) {
            IPiece topPiece = square.getTopPiece();
            if (topPiece != null && topPiece.getColor() == color && topPiece.getType() == Type.FLAT_STONE) {
                amount++;
            }
        }

        return amount;
    }

    @Override
    public void reset() {
        for (ISquare square : getSquares()) {
            square.reset();
        }
    }

}
