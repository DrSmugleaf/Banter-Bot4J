package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Hand {

    @NotNull
    private final Color COLOR;

    private int PIECES;

    private int CAPSTONES;

    Hand(@NotNull Color color, @NotNull Preset preset) {
        COLOR = color;
        PIECES = preset.getStones();
        CAPSTONES = preset.getCapstones();
    }

    @NotNull
    public Color getColor() {
        return COLOR;
    }

    public int getPieces() {
        return PIECES;
    }

    public int getCapstones() {
        return CAPSTONES;
    }

    public int getAmount(@NotNull Type type) {
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

    public boolean has(@NotNull Type type) {
        return getAmount(type) > 0;
    }

    @NotNull
    public Piece takePiece(@NotNull Type type) {
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
