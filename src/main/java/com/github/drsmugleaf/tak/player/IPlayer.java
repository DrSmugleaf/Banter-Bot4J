package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.*;
import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.board.action.IMove;
import com.github.drsmugleaf.tak.board.action.IPlace;
import com.github.drsmugleaf.tak.board.layout.ISquare;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IType;

import java.util.List;
import java.util.Set;

/**
 * Created by DrSmugleaf on 09/01/2020
 */
public interface IPlayer {

    List<IAction> getAvailableActions(IBoard board, IType type);
    List<IAction> getAvailableActions(IBoard board);
    List<IAction> getAvailableActions();
    List<IMove> getAvailableMoves(IBoard board);
    List<IMove> getAvailableMoves();
    List<IPlace> getAvailablePlaces(IBoard board, IType... types);
    List<IPlace> getAvailablePlaces(IBoard board, Set<IType> types);
    List<IPlace> getAvailablePlaces(IBoard board);
    List<IPlace> getAvailablePlaces(IType type);
    List<IPlace> getAvailablePlaces();
    String getName();
    IGame getGame();
    Hand getHand();
    boolean isPassive();
    @Nullable
    IAction getNextAction();
    void setNextAction(IAction action);
    IColor getColor();
    boolean canMove(ISquare origin, ISquare destination, int pieces);
    boolean canMove(int originRow, int originColumn, int destinationRow, int destinationColumn, int pieces);
    ISquare move(ISquare origin, ISquare destination, int pieces);
    ISquare move(int originRow, int originColumn, int destinationRow, int destinationColumn, int pieces);
    boolean canPlace(IType type, int row, int column);
    ISquare place(IType type, int row, int column);
    void surrender();
    void resetPlayer();
    void nextTurn();
    void onEnemyPieceMove(IPlayer player, ISquare origin, ISquare destination, int pieces);
    void onOwnPieceMove(ISquare origin, ISquare destination, int pieces);
    void onEnemyPiecePlace(IPlayer player, IType type, ISquare square);
    void onOwnPiecePlace(IType type, ISquare square);
    void onEnemyTurnEnd(IPlayer player);
    void onOwnTurnEnd();
    void onGameEnd(@Nullable IPlayer winner);

}
