package com.github.drsmugleaf.tak.pieces;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Piece {

    @NotNull
    private final Color COLOR;

    @NotNull
    private Type TYPE;

    public Piece(@NotNull Color color, @NotNull Type type) {
        COLOR = color;
        TYPE = type;
    }

    private Piece(@NotNull Piece piece) {
        this(piece.COLOR, piece.TYPE);
    }

    @NotNull
    public Piece copy() {
        return new Piece(this);
    }

    @NotNull
    public Color getColor() {
        return COLOR;
    }

    @NotNull
    public Type getType() {
        return TYPE;
    }

    public void flatten() {
        if (getType() != Type.STANDING_STONE) {
            throw new IllegalStateException("Piece isn't a standing stone");
        }

        TYPE = Type.FLAT_STONE;
    }

    @Override
    public String toString() {
        return COLOR.toString();
    }

    @NotNull
    public Image toImage(int height, int width) {
        return Images.getImage(this, height, width);
    }

}
