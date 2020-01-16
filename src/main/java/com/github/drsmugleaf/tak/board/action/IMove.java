package com.github.drsmugleaf.tak.board.action;

import com.github.drsmugleaf.tak.board.coordinates.IMovingCoordinates;
import com.github.drsmugleaf.tak.board.layout.IDirection;
import com.google.common.collect.ImmutableList;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public interface IMove extends IAction {

    void validate();
    IMovingCoordinates getFirst();
    ImmutableList<IMovingCoordinates> getCoordinates();
    IDirection getDirection();
    int getTotalAmount();

}
