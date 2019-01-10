package com.github.drsmugleaf.chess.pieces;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 08/01/2019
 */
public class Piece {

    @NotNull
    private final Color COLOR;

    @NotNull
    private final Type TYPE;

    private int row;
    private int column;

    public Piece(@NotNull Color color, @NotNull Type type, int row, int column) {
        COLOR = color;
        TYPE = type;
        this.row = row;
        this.column = column; 
    }

    @NotNull
    public Color getColor() {
        return COLOR;
    }

    @NotNull
    public Type getType() {
        return TYPE;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getForward() {
        return COLOR.getForward();
    }

    @NotNull
    @Override
    public String toString() {
        return TYPE.toString();
    }

}
