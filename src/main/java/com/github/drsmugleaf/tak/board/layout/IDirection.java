package com.github.drsmugleaf.tak.board.layout;

import com.github.drsmugleaf.tak.board.coordinates.ICoordinates;

/**
 * Created by DrSmugleaf on 15/01/2020
 */
public interface IDirection {

    int getDestinationRowOffset();
    int getDestinationColumnOffset();
    boolean isValid(ICoordinates origin, ICoordinates destination);

}
