package com.github.drsmugleaf.tak;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Row {

    @NotNull
    private final Square[] SQUARES;

    Row(int width) {
        SQUARES = new Square[width];
    }

    @NotNull
    public static Row[] getRows(int height, int width) {
        Row[] rows = new Row[height];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Row(width);
        }

        return rows;
    }

    @NotNull
    public Square[] getSquares() {
        return SQUARES;
    }

}
