package com.github.drsmugleaf.tak.board.action;

import com.github.drsmugleaf.tak.board.coordinates.IMovingCoordinates;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public interface IMove extends IAction, IMovingCoordinates {

    int getAmount();
    IMove reverse();

}
