package com.github.drsmugleaf.tak.board.action;

import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.player.IPlayer;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public interface IAction {

    boolean canExecute(IPlayer player);
    boolean canExecute(IPlayer player, IBoard board);
    void execute(IPlayer player, boolean silent);
    <T> T with(IBoard board, IColor nextColor, Function<IBoard, T> function);

}
