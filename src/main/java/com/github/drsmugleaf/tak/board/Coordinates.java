package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class Coordinates implements ICoordinates {

    private int ROW;
    private int COLUMN;
    private final Type PIECE;

    public Coordinates(int column, int row, Type piece) {
        ROW = row;
        COLUMN = column;
        PIECE = piece;
    }

    @Override
    public boolean canPlace(Player player) {
        return player.canPlace(PIECE, COLUMN, ROW);
    }

    @Override
    public void place(Player player) {
        player.place(PIECE, COLUMN, ROW);
    }

    @Override
    public int with(Board board, Color nextColor, Function<Board, Integer> function) {
        Piece piece = new Piece(nextColor, PIECE);
        board.placeSilent(piece, COLUMN, ROW);
        Integer result = function.apply(board);
        board.removeSilent(piece, COLUMN, ROW);

        return result;
    }

}
