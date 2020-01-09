package com.github.drsmugleaf.tak.pieces;

import com.github.drsmugleaf.tak.game.IllegalGameCall;
import com.github.drsmugleaf.tak.images.Images;

import java.awt.*;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Piece implements IPiece {

    private final IColor COLOR;
    private IType TYPE;

    public Piece(IColor color, IType type) {
        COLOR = color;
        TYPE = type;
    }

    private Piece(Piece piece) {
        this(piece.COLOR, piece.TYPE);
    }

    @Override
    public IPiece copy() {
        return new Piece(this);
    }

    @Override
    public IColor getColor() {
        return COLOR;
    }

    @Override
    public IType getType() {
        return TYPE;
    }

    @Override
    public void flatten() {
        if (getType() != Type.STANDING_STONE) {
            throw new IllegalGameCall("Piece isn't a standing stone");
        }

        TYPE = Type.FLAT_STONE;
    }

    @Override
    public String toString() {
        return COLOR.toString() + TYPE.toString();
    }

    @Override
    public Image toImage(int height, int width) {
        return Images.getPiece(this, height, width);
    }

    @Override
    public double toDouble() {
        return getColor().toDouble() * getType().toDouble();
    }

}
