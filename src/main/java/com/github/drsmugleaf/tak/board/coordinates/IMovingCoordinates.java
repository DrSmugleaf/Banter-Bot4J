package com.github.drsmugleaf.tak.board.coordinates;

import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.layout.ISquare;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public interface IMovingCoordinates extends ICoordinates {

    int getDestinationRow();
    int getDestinationColumn();
    ISquare toDestination(IBoard board);

}
