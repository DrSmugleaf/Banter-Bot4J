package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.board.ISquare;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IType;

import java.util.List;
import java.util.Set;

/**
 * Created by DrSmugleaf on 09/01/2020
 */
public interface IPlayer {

    List<ICoordinates> getAvailableActions(IBoard board, IType type);
    List<ICoordinates> getAvailableActions(IBoard board);
    List<ICoordinates> getAvailableActions();
    List<ICoordinates> getAvailableMoves(IBoard board);
    List<ICoordinates> getAvailableMoves();
    List<ICoordinates> getAvailablePlaces(IBoard board, IType... types);
    List<ICoordinates> getAvailablePlaces(IBoard board, Set<IType> types);
    List<ICoordinates> getAvailablePlaces(IBoard board);
    List<ICoordinates> getAvailablePlaces(IType type);
    List<ICoordinates> getAvailablePlaces();
    String getName();
    IGame getGame();
    Hand getHand();
    boolean isPassive();
    @Nullable
    ICoordinates getNextAction();
    void setNextAction(ICoordinates action);
    IColor getColor();
    boolean canMove(ISquare origin, ISquare destination, int pieces);
    boolean canMove(int originColumn, int originRow, int destinationColumn, int destinationRow, int pieces);
    ISquare move(ISquare origin, ISquare destination, int pieces);
    ISquare move(int originColumn, int originRow, int destinationColumn, int destinationRow, int pieces);
    boolean canPlace(IType type, int column, int row);
    ISquare place(IType type, int column, int row);
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
