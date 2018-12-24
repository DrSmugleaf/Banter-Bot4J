package com.github.drsmugleaf.tak.pieces;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Piece {

    @NotNull
    private final Color COLOR;

    @NotNull
    private final Type TYPE;

    public Piece(@NotNull Color color, @NotNull Type type) {
        COLOR = color;
        TYPE = type;
    }

    @NotNull
    public Color getColor() {
        return COLOR;
    }

    @NotNull
    public Type getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return COLOR.toString();
    }

}
