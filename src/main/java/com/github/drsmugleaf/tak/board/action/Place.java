package com.github.drsmugleaf.tak.board.action;

import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.coordinates.Coordinates;
import com.github.drsmugleaf.tak.board.layout.ISquare;
import com.github.drsmugleaf.tak.pieces.IType;
import com.github.drsmugleaf.tak.player.IPlayer;

import java.util.Objects;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public class Place extends Coordinates implements IPlace {

    private final IType TYPE;

    public Place(int row, int column, IType type) {
        super(row, column);
        TYPE = type;
    }

    public Place(ISquare square, IType type) {
        super(square);
        TYPE = type;
    }

    @Override
    public IType getType() {
        return TYPE;
    }

    @Override
    public boolean canExecute(IPlayer player) {
        return canExecute(player, player.getGame().getBoard());
    }

    @Override
    public boolean canExecute(IPlayer player, IBoard board) {
        return player.canPlace(this, board);
    }

    @Override
    public int execute(IPlayer player, boolean silent) {
        return player.place(this, silent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;
        if (!super.equals(o)) return false;
        Place place = (Place) o;
        return getType().equals(place.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType());
    }

}
