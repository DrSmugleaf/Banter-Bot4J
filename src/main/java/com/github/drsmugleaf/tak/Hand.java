package com.github.drsmugleaf.tak;

import com.github.drsmugleaf.tak.pieces.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Hand {

    @NotNull
    private final Color COLOR;

    private int PIECES;

    private Hand(@NotNull Color color, int pieces) {
        COLOR = color;
        PIECES = pieces;
    }

    @NotNull
    public Color getColor() {
        return COLOR;
    }

    public int getPieces() {
        return PIECES;
    }

}
