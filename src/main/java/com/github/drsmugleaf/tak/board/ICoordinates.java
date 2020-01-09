package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.IPlayer;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 27/08/2019
 */
public interface ICoordinates {

    boolean canPlace(IPlayer player);
    void place(IPlayer player);
    int with(IBoard board, Color nextColor, Function<IBoard, Integer> function);

}
