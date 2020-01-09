package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;

/**
 * Created by DrSmugleaf on 09/01/2020
 */
public interface ILine {

    ISquare[] getSquares();
    boolean canMove(int origin, ISquare destination, int pieces);
    ISquare move(int origin, ISquare destination, int pieces, boolean silent);
    boolean canPlace(int index);
    ISquare place(Piece piece, int location, boolean silent);
    ISquare remove(Piece piece, int location, boolean silent);
    void setSquare(int index, ISquare square);
    boolean hasSquare(Color color);
    int countFlat(Color color);
    void reset();

}
