package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/01/2019
 */
public class MovingCoordinates extends Coordinates {

    @NotNull
    private final Square ORIGIN;

    @NotNull
    private final Square DESTINATION;

    private final int PIECES;

    public MovingCoordinates(@NotNull Square origin, @NotNull Square destination, int pieces) {
        super(origin.getColumn(), destination.getRow(), origin.getTopPiece().getType());
        ORIGIN = origin;
        DESTINATION = destination;
        PIECES = pieces;
    }

    public boolean canMove(@NotNull Player player) {
        return player.canMove(ORIGIN, DESTINATION, PIECES);
    }

    public void move(@NotNull Player player) {
        player.move(ORIGIN, DESTINATION, PIECES);
    }

    @Override
    public boolean canPlace(@NotNull Player player) {
        return canMove(player);
    }

    @Override
    public void place(@NotNull Player player) {
        move(player);
    }

}
