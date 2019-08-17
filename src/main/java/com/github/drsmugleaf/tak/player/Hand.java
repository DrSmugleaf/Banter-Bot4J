package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Hand {

    private final Color COLOR;
    private int PIECES;
    private int CAPSTONES;

    Hand(Color color, Preset preset) {
        COLOR = color;
        PIECES = preset.getStones();
        CAPSTONES = preset.getCapstones();
    }

    public Color getColor() {
        return COLOR;
    }

    public int getPieces() {
        return PIECES;
    }

    public int getCapstones() {
        return CAPSTONES;
    }

    public int getAmount(Type type) {
        switch (type) {
            case FLAT_STONE:
            case STANDING_STONE:
                return getPieces();
            case CAPSTONE:
                return getCapstones();
            default:
                throw new IllegalArgumentException("Unrecognized piece type: " + type);
        }
    }

    public boolean has(Type type) {
        return getAmount(type) > 0;
    }

    public boolean hasAny() {
        return has(Type.FLAT_STONE) || has(Type.STANDING_STONE) || has(Type.CAPSTONE);
    }

    public Piece takePiece(Type type) {
        if (!has(type)) {
            throw new IllegalArgumentException("No pieces left in this hand of type " + type);
        }

        switch (type) {
            case FLAT_STONE:
            case STANDING_STONE:
                PIECES--;
                break;
            case CAPSTONE:
                CAPSTONES--;
                break;
        }

        return new Piece(COLOR, type);
    }

}
