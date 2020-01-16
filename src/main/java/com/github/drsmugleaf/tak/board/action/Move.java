package com.github.drsmugleaf.tak.board.action;

import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.coordinates.IMovingCoordinates;
import com.github.drsmugleaf.tak.board.layout.IDirection;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public class Move implements IMove {

    private final ImmutableList<IMovingCoordinates> COORDINATES;
    private final IDirection DIRECTION;
    private final int TOTAL_AMOUNT;

    public Move(Collection<IMovingCoordinates> coordinates, IDirection direction) {
        COORDINATES = ImmutableList.copyOf(coordinates);
        DIRECTION = direction;
        TOTAL_AMOUNT = getCoordinates().stream().map(IMovingCoordinates::getAmount).mapToInt(i -> i).sum();

        validate();
    }

    @Override
    public void validate() {
        int size = getCoordinates().size();
        if (size < 2) {
            throw new IllegalStateException("A move must have at least two coordinates, found: " + size);
        }

        IMovingCoordinates last = null;
        for (IMovingCoordinates coordinates : getCoordinates()) {
            if (coordinates.getAmount() < 1) {
                throw new IllegalStateException("Moved pieces must be bigger than or equal to 1");
            }

            if (last == null) {
                last = coordinates;
                continue;
            }

            if (last.getRow() != coordinates.getRow() && last.getColumn() != coordinates.getColumn()) {
                throw new IllegalStateException("Row or column from " + coordinates + " don't match " + last);
            }

            if (!getDirection().isValid(last, coordinates)) {
                throw new IllegalStateException("Direction " + getDirection() + " isn't valid for origin " + last + " and destination " + coordinates);
            }

            last = coordinates;
        }
    }

    @Override
    public boolean canExecute(IPlayer player) {
        return player.canMove(this);
    }

    @Override
    public boolean canExecute(IPlayer player, IBoard board) {
        return player.canMove(this, board);
    }

    @Override
    public void execute(IPlayer player, boolean silent) {
        player.move(this, silent);
    }

    @Override
    public <T> T with(IBoard board, IColor nextColor, Function<IBoard, T> function) {
        board.move(this, true);
        T result = function.apply(board);
        board.restore();

        return result;
    }

    @Override
    public IMovingCoordinates getFirst() {
        return getCoordinates().get(0);
    }

    @Override
    public ImmutableList<IMovingCoordinates> getCoordinates() {
        return COORDINATES;
    }

    @Override
    public IDirection getDirection() {
        return DIRECTION;
    }

    @Override
    public int getTotalAmount() {
        return TOTAL_AMOUNT;
    }

}
