package com.github.drsmugleaf.tak.board.layout;

import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IPiece;

/**
 * Created by DrSmugleaf on 09/01/2020
 */
public interface ILine {

    ISquare[] getSquares();
    boolean canMove(int origin, ISquare destination, int pieces);
    ISquare move(int origin, ISquare destination, int pieces, boolean silent);
    boolean canPlace(int index);
    ISquare place(IPiece piece, int location, boolean silent);
    ISquare remove(IPiece piece, int location, boolean silent);
    void setSquare(int index, ISquare square);
    boolean hasSquare(IColor color);
    int countFlat(IColor color);
    void reset();

}
