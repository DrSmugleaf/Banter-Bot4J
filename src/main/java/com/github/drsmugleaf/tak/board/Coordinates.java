package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.*;
import com.github.drsmugleaf.tak.player.IPlayer;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class Coordinates implements ICoordinates {

    private int ROW;
    private int COLUMN;
    private final IType PIECE;

    public Coordinates(int column, int row, IType piece) {
        ROW = row;
        COLUMN = column;
        PIECE = piece;
    }

    public Coordinates(ISquare square, IType piece) {
        this(square.getColumn(), square.getRow(), piece);
    }

    @Override
    public boolean canPlace(IPlayer player) {
        return player.canPlace(PIECE, COLUMN, ROW);
    }

    @Override
    public void place(IPlayer player) {
        player.place(PIECE, COLUMN, ROW);
    }

    @Override
    public int with(IBoard board, IColor nextColor, Function<IBoard, Integer> function) {
        IPiece piece = new Piece(nextColor, PIECE);
        board.placeSilent(piece, COLUMN, ROW);
        Integer result = function.apply(board);
        board.removeSilent(piece, COLUMN, ROW);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return ROW == that.ROW &&
                COLUMN == that.COLUMN &&
                PIECE.equals(that.PIECE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ROW, COLUMN, PIECE);
    }

}
