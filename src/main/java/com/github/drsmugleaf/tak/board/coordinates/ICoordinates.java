package com.github.drsmugleaf.tak.board.coordinates;

import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.layout.ISquare;

/**
 * Created by DrSmugleaf on 27/08/2019
 */
public interface ICoordinates {

    int getRow();
    int getColumn();
    ISquare toSquare(IBoard board);

}
