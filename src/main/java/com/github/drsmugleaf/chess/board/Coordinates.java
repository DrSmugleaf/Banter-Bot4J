package com.github.drsmugleaf.chess.board;

/**
 * Created by DrSmugleaf on 08/01/2019
 */
public class Coordinates {

    private final int ROW;
    private final int COLUMN;

    public Coordinates(int row, int column) {
        ROW = row;
        COLUMN = column;
    }

    public int getRow() {
        return ROW;
    }

    public int getColumn() {
        return COLUMN;
    }

}
