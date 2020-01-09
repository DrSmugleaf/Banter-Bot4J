package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.board.IPreset;
import com.github.drsmugleaf.tak.pieces.*;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Hand {

    private final IPreset PRESET;
    private final IColor COLOR;
    private int STONES;
    private int CAPSTONES;

    Hand(IColor color, IPreset preset) {
        PRESET = preset;
        COLOR = color;
        STONES = preset.getStones();
        CAPSTONES = preset.getCapstones();
    }

    public IPreset getPreset() {
        return PRESET;
    }

    public IColor getColor() {
        return COLOR;
    }

    public int getCapstones() {
        return CAPSTONES;
    }

    public int getStones() {
        return STONES;
    }

    public int getAmount(IType type) {
        if (type.isCapstone()) {
            return getCapstones();
        } else if (type.isFlatStone() || type.isStandingStone()) {
            return getStones();
        } else {
            throw new IllegalArgumentException("Unrecognized piece type: " + type);
        }
    }

    public boolean has(IType type) {
        return getAmount(type) > 0;
    }

    public boolean hasAny() {
        for (IType type : Type.getTypes()) {
            if (has(type)) {
                return true;
            }
        }

        return false;
    }

    public IPiece takePiece(IType type) {
        if (!has(type)) {
            throw new IllegalArgumentException("No pieces left in this hand of type " + type);
        }

        if (type.isCapstone()) {
            CAPSTONES--;
        } else if (type.isFlatStone() || type.isStandingStone()) {
            STONES--;
        } else {
            throw new IllegalArgumentException("Unrecognized piece type: " + type);
        }

        return new Piece(COLOR, type);
    }

    public void reset() {
        STONES = getPreset().getStones();
        CAPSTONES = getPreset().getCapstones();
    }

}
