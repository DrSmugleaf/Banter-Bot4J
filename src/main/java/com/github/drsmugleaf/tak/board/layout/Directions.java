package com.github.drsmugleaf.tak.board.layout;

import com.github.drsmugleaf.tak.board.coordinates.ICoordinates;

/**
 * Created by DrSmugleaf on 15/01/2020
 */
public enum Directions implements IDirection {

    UP(1, 0),
    RIGHT(0, -1),
    DOWN(-1, 0),
    LEFT(0, 1);

    private final int DESTINATION_ROW_OFFSET;
    private final int DESTINATION_COLUMN_OFFSET;

    Directions(int destinationRowOffset, int destinationColumnOffset) {
        DESTINATION_ROW_OFFSET = destinationRowOffset;
        DESTINATION_COLUMN_OFFSET = destinationColumnOffset;
    }

    @Override
    public int getDestinationRowOffset() {
        return DESTINATION_ROW_OFFSET;
    }

    @Override
    public int getDestinationColumnOffset() {
        return DESTINATION_COLUMN_OFFSET;
    }

    @Override
    public boolean isValid(ICoordinates origin, ICoordinates destination) {
        return origin.getRow() == destination.getRow() + getDestinationRowOffset() &&
                origin.getColumn() == destination.getColumn() + getDestinationColumnOffset();
    }

}
