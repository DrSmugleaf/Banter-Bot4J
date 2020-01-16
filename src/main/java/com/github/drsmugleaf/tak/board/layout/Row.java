package com.github.drsmugleaf.tak.board.layout;

import com.github.drsmugleaf.tak.board.action.IPlace;
import com.github.drsmugleaf.tak.pieces.IPiece;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public class Row extends Line {

    public Row(int size) {
        super(size);
    }

    public boolean canPlace(IPlace place) {
        return super.canPlace(place.getColumn());
    }

    public ISquare place(IPiece piece, IPlace place, boolean silent) {
        return super.place(piece, place.getColumn(), silent);
    }

    public ISquare remove(IPiece piece, IPlace place, boolean silent) {
        return super.remove(piece, place.getColumn(), silent);
    }

}
