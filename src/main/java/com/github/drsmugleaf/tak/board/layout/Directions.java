package com.github.drsmugleaf.tak.board.layout;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.coordinates.Coordinates;
import com.github.drsmugleaf.tak.board.coordinates.ICoordinates;

/**
 * Created by DrSmugleaf on 15/01/2020
 */
public enum Directions implements IDirection {

    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    private final int ROW_OFFSET;
    private final int COLUMN_OFFSET;

    Directions(int destinationRowOffset, int destinationColumnOffset) {
        ROW_OFFSET = destinationRowOffset;
        COLUMN_OFFSET = destinationColumnOffset;
    }

    @Override
    public int getRowOffset() {
        return ROW_OFFSET;
    }

    @Override
    public int getColumnOffset() {
        return COLUMN_OFFSET;
    }

    @Override
    public boolean isValid(ICoordinates origin, ICoordinates destination) {
        return origin.getRow() == destination.getRow() - getRowOffset() &&
                origin.getColumn() == destination.getColumn() - getColumnOffset();
    }

    @Nullable
    @Override
    public ISquare get(IBoard board, ISquare origin) {
        int row = origin.getRow() + getRowOffset();
        int column = origin.getColumn() + getColumnOffset();
        if (row < 0 || row >= board.getPreset().getSize() || column < 0 || column >= board.getPreset().getSize()) {
            return null;
        }

        return board.getRows()[row].getSquares()[column];
    }

    @Override
    public ICoordinates get(int row, int column, int offset) {
        return new Coordinates(
                row + getRowOffset() * offset,
                column + getColumnOffset() * offset
        );
    }

}
