package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Hand {

    private final Preset PRESET;
    private final Color COLOR;
    private int STONES;
    private int CAPSTONES;

    Hand(Color color, Preset preset) {
        PRESET = preset;
        COLOR = color;
        STONES = preset.getStones();
        CAPSTONES = preset.getCapstones();
    }

    public Preset getPreset() {
        return PRESET;
    }

    public Color getColor() {
        return COLOR;
    }

    public int getCapstones() {
        return CAPSTONES;
    }

    public int getStones() {
        return STONES;
    }

    public int getAmount(Type type) {
        switch (type) {
            case CAPSTONE:
                return getCapstones();
            case FLAT_STONE:
            case STANDING_STONE:
                return getStones();
            default:
                throw new IllegalArgumentException("Unrecognized piece type: " + type);
        }
    }

    public boolean has(Type type) {
        return getAmount(type) > 0;
    }

    public boolean hasAny() {
        for (Type type : Type.getTypes()) {
            if (has(type)) {
                return true;
            }
        }

        return false;
    }

    public Piece takePiece(Type type) {
        if (!has(type)) {
            throw new IllegalArgumentException("No pieces left in this hand of type " + type);
        }

        switch (type) {
            case CAPSTONE:
                CAPSTONES--;
                break;
            case FLAT_STONE:
            case STANDING_STONE:
                STONES--;
                break;
        }

        return new Piece(COLOR, type);
    }

    public void reset() {
        STONES = getPreset().getStones();
        CAPSTONES = getPreset().getCapstones();
    }

}
