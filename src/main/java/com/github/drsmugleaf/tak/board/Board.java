package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Board {

    @NotNull
    private final Row[] PIECES;

    @NotNull
    private final Sizes SIZE;

    private Board(@NotNull Sizes size) {
        SIZE = size;

        Row[] rows = new Row[size.getSize()];

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Row(size.getSize());
        }

        PIECES = rows;
    }

    @NotNull
    public Board getDefault() {
        return new Board(Sizes.FIVE);
    }

    public boolean canPlace(@NotNull Type type, int row, int square) {
        return row < PIECES.length && PIECES[row].canPlace(type, square);
    }

    public void place(@NotNull Piece piece, int row, int square) {
        PIECES[row].place(piece, square);
    }

}
