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
    public Square[] getSquares() {
        return SQUARES;
    }

}
