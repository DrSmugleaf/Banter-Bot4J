package com.github.drsmugleaf.tak.board.coordinates;

import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.layout.ISquare;

import java.util.Objects;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class Coordinates implements ICoordinates {

    private int ROW;
    private int COLUMN;

    public Coordinates(int row, int column) {
        ROW = row;
        COLUMN = column;
    }

    public Coordinates(ISquare square) {
        this(square.getRow(), square.getColumn());
    }

    @Override
    public int getRow() {
        return ROW;
    }

    @Override
    public int getColumn() {
        return COLUMN;
    }

    @Override
    public ISquare toSquare(IBoard board) {
        return board.getRows()[getRow()].getSquares()[getColumn()];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return getRow() == that.getRow() &&
                getColumn() == that.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn());
    }

}
