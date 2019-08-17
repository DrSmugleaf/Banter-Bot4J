package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.Player;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 01/01/2019
 */
public class MovingCoordinates extends Coordinates {

    private final Square ORIGIN;
    private final Square DESTINATION;
    private final int PIECES;

    public MovingCoordinates(Square origin, Square destination, int pieces) {
        super(origin.getColumn(), destination.getRow(), origin.getTopPiece().getType());
        ORIGIN = origin;
        DESTINATION = destination;
        PIECES = pieces;
    }

    @Override
    public boolean canPlace(Player player) {
        return player.canMove(ORIGIN, DESTINATION, PIECES);
    }

    @Override
    public void place(Player player) {
        player.move(ORIGIN, DESTINATION, PIECES);
    }

    @Override
    public int with(Board board, Color nextPlayer, Function<Board, Integer> function) {
        board.moveSilent(ORIGIN, DESTINATION, PIECES);
        Integer result = function.apply(board);
        board.moveSilent(DESTINATION, ORIGIN, PIECES);

        return result;
    }

}
