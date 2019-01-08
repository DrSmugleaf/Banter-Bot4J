package com.github.drsmugleaf.chess.board;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 08/01/2019
 */
public class Board {

    @NotNull
    private final Line[] LINES = new Line[8];

    public Board() {
        for (int i = 0; i < LINES.length; i++) {
            LINES[i] = new Line();
        }
    }

    @NotNull
    public Line[] getLines() {
        return LINES;
    }

}
