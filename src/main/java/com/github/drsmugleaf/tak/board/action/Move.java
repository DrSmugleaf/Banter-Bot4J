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
        IMovingCoordinates last = null;
        for (IMovingCoordinates coordinates : getCoordinates()) {
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
    public void execute(IPlayer player) {
        player.move(this, false);
    }

    @Override
    public int with(IBoard board, IColor nextColor, Function<IBoard, Integer> function) {
        board.move(this, true);
        Integer result = function.apply(board);
        board.reverseMove(this);

        return result;
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
