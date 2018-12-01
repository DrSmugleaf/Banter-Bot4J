package com.github.drsmugleaf.tak;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Board {

    @NotNull
    private final Row[] PIECES;

    private Board(int height, int width) {
        PIECES = Row.getRows(height, width);
    }

    @NotNull
    public Board getDefault() {
        return new Board(5, 5);
    }

}
