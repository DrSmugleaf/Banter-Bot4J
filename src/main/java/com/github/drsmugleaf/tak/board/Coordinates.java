package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class Coordinates {

    private int ROW;
    private int COLUMN;
    private final Type PIECE;

    public Coordinates(int column, int row, Type piece) {
        ROW = row;
        COLUMN = column;
        PIECE = piece;
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

    public Type getPiece() {
        return PIECE;
    }

    public boolean canPlace(Player player) {
        return player.canPlace(PIECE, COLUMN, ROW);
    }

    public void place(Player player) {
        player.place(PIECE, COLUMN, ROW);
    }

    public int with(Board board, Color nextPlayer, Function<Board, Integer> function) {
        Piece piece = new Piece(nextPlayer, PIECE);
        board.placeSilent(piece, COLUMN, ROW);
        Integer result = function.apply(board);
        board.removeSilent(piece, COLUMN, ROW);

        return result;
    }

}
