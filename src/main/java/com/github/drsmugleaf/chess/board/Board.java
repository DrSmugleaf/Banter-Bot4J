package com.github.drsmugleaf.chess.board;

import com.github.drsmugleaf.chess.pieces.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 08/01/2019
 */
public class Board {

    @NotNull
    private final Line[] LINES = new Line[8];

    public Board() {
        LINES[0] = Line.getMainLine(Color.WHITE);
        LINES[1] = Line.getPawnLine(Color.WHITE);

        for (int i = 2; i < 7; i++) {
            LINES[i] = Line.getEmptyLine();
        }

        LINES[6] = Line.getPawnLine(Color.BLACK);
        LINES[7] = Line.getMainLine(Color.BLACK);
    }

    @NotNull
    public Line[] getLines() {
        return LINES;
    }

}
