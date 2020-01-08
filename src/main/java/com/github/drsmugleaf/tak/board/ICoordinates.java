package com.github.drsmugleaf.tak.board;

import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.Player;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 27/08/2019
 */
public interface ICoordinates {

    boolean canPlace(Player player);
    void place(Player player);
    int with(IBoard board, Color nextColor, Function<IBoard, Integer> function);

}
