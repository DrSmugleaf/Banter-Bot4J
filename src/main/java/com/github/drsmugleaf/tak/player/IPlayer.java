package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.board.action.IMove;
import com.github.drsmugleaf.tak.board.action.IPlace;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.pieces.IColor;

import java.util.List;

/**
 * Created by DrSmugleaf on 09/01/2020
 */
public interface IPlayer {

    List<IAction> getAvailableActions();
    List<IAction> getAvailableActions(IBoard board);
    String getName();
    IGame getGame();
    Hand getHand();
    boolean isPassive();
    @Nullable
    IAction getNextAction();
    void setNextAction(IAction action);
    IColor getColor();
    boolean canMove(IMove move);
    boolean canMove(IMove move, IBoard board);
    int move(IMove move, boolean silent);
    boolean canPlace(IPlace place);
    boolean canPlace(IPlace place, IBoard board);
    int place(IPlace place, boolean silent);
    void surrender();
    void resetPlayer();
    void nextTurn();
    void onEnemyPieceMove(IPlayer player, IMove move);
    void onOwnPieceMove(IMove move);
    void onEnemyPiecePlace(IPlayer player, IPlace place);
    void onOwnPiecePlace(IPlace place);
    void onEnemyTurnEnd(IPlayer player);
    void onOwnTurnEnd();
    void onGameEnd(@Nullable IPlayer winner);

}
