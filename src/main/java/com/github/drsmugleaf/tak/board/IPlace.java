package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.IType;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public interface IPlace extends ICoordinates, IAction {

    IType getType();

}
