package com.github.drsmugleaf.tak.board.layout;

import com.github.drsmugleaf.tak.board.action.IMove;
import com.github.drsmugleaf.tak.board.action.IPlace;
import com.github.drsmugleaf.tak.pieces.IPiece;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public class Column extends Line {

    public Column(int size) {
        super(size);
    }

    public boolean canMove(IMove move, ISquare destination) {
        return super.canMove(move.getRow(), move, destination);
    }

    public ISquare move(IMove move, ISquare destination, boolean silent) {
        return super.move(move.getRow(), move, destination, silent);
    }

    public boolean canPlace(IPlace place) {
        return super.canPlace(place.getRow());
    }

    public ISquare place(IPiece piece, IPlace place, boolean silent) {
        return super.place(piece, place.getRow(), silent);
    }

    public ISquare remove(IPiece piece, IPlace place, boolean silent) {
        return super.remove(piece, place.getRow(), silent);
    }

}
