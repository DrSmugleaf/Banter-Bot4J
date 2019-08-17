package com.github.drsmugleaf.tak.pieces;

import com.github.drsmugleaf.tak.Images;

import java.awt.*;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Piece {

    private final Color COLOR;
    private Type TYPE;

    public Piece(Color color, Type type) {
        COLOR = color;
        TYPE = type;
    }

    private Piece(Piece piece) {
        this(piece.COLOR, piece.TYPE);
    }

    public Piece copy() {
        return new Piece(this);
    }

    public Color getColor() {
        return COLOR;
    }

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

    public Image toImage(int height, int width) {
        return Images.getPiece(this, height, width);
    }

}
