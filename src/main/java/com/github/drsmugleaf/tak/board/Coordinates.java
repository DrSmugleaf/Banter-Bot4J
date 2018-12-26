package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class Coordinates {

    private int ROW;
    private int COLUMN;

    public Coordinates(int row, int column) {
        ROW = row;
        COLUMN = column;
    }

    public int getRow() {
        return ROW;
    }

    public void setRow(int row) {
        ROW = row;
    }

    public int getColumn() {
        return COLUMN;
    }

    public void setColumn(int column) {
        COLUMN = column;
    }

    public void place(@NotNull Player player, @NotNull Type type) {
        player.place(type, ROW, COLUMN);
    }

}
