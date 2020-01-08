package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.Player;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 01/01/2019
 */
public class MovingCoordinates implements ICoordinates {

    private final int ORIGIN_COLUMN;
    private final int ORIGIN_ROW;
    private final int DESTINATION_COLUMN;
    private final int DESTINATION_ROW;
    private final int PIECES;

    public MovingCoordinates(int originColumn, int originRow, int destinationColumn, int destinationRow, int pieces) {
        ORIGIN_COLUMN = originColumn;
        ORIGIN_ROW = originRow;
        DESTINATION_COLUMN = destinationColumn;
        DESTINATION_ROW = destinationRow;
        PIECES = pieces;
    }

    public MovingCoordinates(ISquare origin, ISquare destination, int pieces) {
        this(origin.getColumn(), origin.getRow(), destination.getColumn(), destination.getRow(), pieces);
    }

    @Override
    public boolean canPlace(Player player) {
        return player.canMove(ORIGIN_COLUMN, ORIGIN_ROW, DESTINATION_COLUMN, DESTINATION_ROW, PIECES);
    }

    @Override
    public void place(Player player) {
        player.move(ORIGIN_COLUMN, ORIGIN_ROW, DESTINATION_COLUMN, DESTINATION_ROW, PIECES);
    }

    @Override
    public int with(Board board, Color nextColor, Function<Board, Integer> function) {
        board.moveSilent(ORIGIN_COLUMN, ORIGIN_ROW, DESTINATION_COLUMN, DESTINATION_ROW, PIECES);
        Integer result = function.apply(board);
        board.moveSilent(ORIGIN_COLUMN, ORIGIN_ROW, DESTINATION_COLUMN, DESTINATION_ROW, PIECES);

        return result;
    }

}
