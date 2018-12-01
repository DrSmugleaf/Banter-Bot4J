package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.board.Sizes;
import com.github.drsmugleaf.tak.pieces.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Hand {

    @NotNull
    private final Color COLOR;

    private int PIECES;

    private int CAPSTONES;

    Hand(@NotNull Color color, @NotNull Sizes size) {
        COLOR = color;
        PIECES = size.getStones();
        CAPSTONES = size.getCapstones();
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

}
