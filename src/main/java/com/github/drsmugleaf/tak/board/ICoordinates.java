package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IType;
import com.github.drsmugleaf.tak.player.IPlayer;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 27/08/2019
 */
public interface ICoordinates {

    int getRow();
    int getColumn();

}
