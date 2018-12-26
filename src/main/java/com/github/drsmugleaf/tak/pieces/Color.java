package com.github.drsmugleaf.tak.pieces;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public enum Color {

    BLACK,
    WHITE;

    @NotNull
    public Color getOpposite() {
        switch (this) {
            case BLACK:
                return WHITE;
            case WHITE:
                return BLACK;
            default:
                throw new IllegalStateException("Unrecognized color: " + this);
        }
    }

}
