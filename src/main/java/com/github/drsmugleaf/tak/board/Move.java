package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.player.IPlayer;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public class Move extends MovingCoordinates implements IMove {

    private final int AMOUNT;

    public Move(int originRow, int originColumn, int destinationRow, int destinationColumn, int amount) {
        super(originRow, originColumn, destinationRow, destinationColumn);
        AMOUNT = amount;
    }

    public Move(ISquare origin, ISquare destination, int amount) {
        super(origin, destination);
        AMOUNT = amount;
    }

    @Override
    public int getAmount() {
        return AMOUNT;
    }

    @Override
    public boolean canExecute(IPlayer player) {
        return player.canMove(getOriginRow(), getOriginColumn(), getDestinationRow(), getDestinationColumn(), getAmount());
    }

    @Override
    public void execute(IPlayer player) {
        player.move(getOriginRow(), getOriginColumn(), getDestinationRow(), getDestinationColumn(), getAmount());
    }

    @Override
    public int with(IBoard board, IColor nextColor, Function<IBoard, Integer> function) {
        board.moveSilent(getOriginRow(), getOriginColumn(), getDestinationRow(), getDestinationColumn(), getAmount());
        Integer result = function.apply(board);
        board.moveSilent(getDestinationRow(), getDestinationColumn(), getOriginRow(), getOriginColumn(), getAmount());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        if (!super.equals(o)) return false;
        Move move = (Move) o;
        return getAmount() == move.getAmount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAmount());
    }

}
