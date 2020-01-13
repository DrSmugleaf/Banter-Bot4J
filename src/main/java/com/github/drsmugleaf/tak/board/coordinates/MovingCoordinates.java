package com.github.drsmugleaf.tak.board.coordinates;

import com.github.drsmugleaf.tak.board.layout.ISquare;

import java.util.Objects;

/**
 * Created by DrSmugleaf on 01/01/2019
 */
public class MovingCoordinates implements IMovingCoordinates {

    private final int ORIGIN_ROW;
    private final int ORIGIN_COLUMN;
    private final int DESTINATION_ROW;
    private final int DESTINATION_COLUMN;

    public MovingCoordinates(int originRow, int originColumn, int destinationRow, int destinationColumn) {
        ORIGIN_ROW = originRow;
        ORIGIN_COLUMN = originColumn;
        DESTINATION_ROW = destinationRow;
        DESTINATION_COLUMN = destinationColumn;
    }

    public MovingCoordinates(ISquare origin, ISquare destination) {
        this(origin.getRow(), origin.getColumn(), destination.getRow(), destination.getColumn());
    }

    @Override
    public int getOriginRow() {
        return ORIGIN_ROW;
    }

    @Override
    public int getOriginColumn() {
        return ORIGIN_COLUMN;
    }

    @Override
    public int getDestinationRow() {
        return DESTINATION_ROW;
    }

    @Override
    public int getDestinationColumn() {
        return DESTINATION_COLUMN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovingCoordinates)) return false;
        MovingCoordinates that = (MovingCoordinates) o;
        return getOriginRow() == that.getOriginRow() &&
                getOriginColumn() == that.getOriginColumn() &&
                getDestinationRow() == that.getDestinationRow() &&
                getDestinationColumn() == that.getDestinationColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOriginRow(), getOriginColumn(), getDestinationRow(), getDestinationColumn());
    }

}
