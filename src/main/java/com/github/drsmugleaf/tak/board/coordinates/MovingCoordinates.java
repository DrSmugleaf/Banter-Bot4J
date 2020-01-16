package com.github.drsmugleaf.tak.board.coordinates;

import com.github.drsmugleaf.tak.board.layout.ISquare;

/**
 * Created by DrSmugleaf on 01/01/2019
 */
public class MovingCoordinates extends Coordinates implements IMovingCoordinates {

    private final int AMOUNT;

    public MovingCoordinates(int row, int column, int amount) {
        super(row, column);
        AMOUNT = amount;
    }

    public MovingCoordinates(ISquare origin, int amount) {
        this(origin.getRow(), origin.getColumn(), amount);
    }

    @Override
    public int getAmount() {
        return AMOUNT;
    }

    @Override
    public String toString() {
        return "Row: " + getRow() + ", column: " + getColumn() + ", amount: " + getAmount();
    }

}
