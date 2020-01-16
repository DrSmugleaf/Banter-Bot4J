package com.github.drsmugleaf.tak.board.action;

import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.layout.ISquare;
import com.github.drsmugleaf.tak.board.coordinates.Coordinates;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IPiece;
import com.github.drsmugleaf.tak.pieces.IType;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.player.IPlayer;

import java.util.Objects;
import java.util.function.Function;

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
    public void execute(IPlayer player) {
        player.place(this, false);
    }

    @Override
    public <T> T with(IBoard board, IColor nextColor, Function<IBoard, T> function) {
        IPiece piece = new Piece(nextColor, getType());
        board.place(piece, this, true);
        T result = function.apply(board);
        board.remove(piece, this, true);

        return result;
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
