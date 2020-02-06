package com.github.drsmugleaf.tak.board.action;

import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.player.IPlayer;

/**
 * Created by DrSmugleaf on 13/01/2020
 */
public interface IAction {

    boolean canExecute(IPlayer player);
    boolean canExecute(IPlayer player, IBoard board);
    int execute(IPlayer player, boolean silent);

}
