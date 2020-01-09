package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.board.ISquare;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;

import java.util.List;
import java.util.Set;

/**
 * Created by DrSmugleaf on 09/01/2020
 */
public interface IPlayer {

    List<ICoordinates> getAvailableActions(IBoard board, Type type);
    List<ICoordinates> getAvailableActions(IBoard board);
    List<ICoordinates> getAvailableActions();
    List<ICoordinates> getAvailableMoves(IBoard board);
    List<ICoordinates> getAvailableMoves();
    List<ICoordinates> getAvailablePlaces(IBoard board, Type... types);
    List<ICoordinates> getAvailablePlaces(IBoard board, Set<Type> types);
    List<ICoordinates> getAvailablePlaces(IBoard board);
    List<ICoordinates> getAvailablePlaces(Type type);
    List<ICoordinates> getAvailablePlaces();
    String getName();
    IGame getGame();
    Hand getHand();
    boolean isPassive();
    @Nullable
    ICoordinates getNextAction();
    void setNextAction(ICoordinates action);
    Color getColor();
    boolean canMove(ISquare origin, ISquare destination, int pieces);
    boolean canMove(int originColumn, int originRow, int destinationColumn, int destinationRow, int pieces);
    ISquare move(ISquare origin, ISquare destination, int pieces);
    ISquare move(int originColumn, int originRow, int destinationColumn, int destinationRow, int pieces);
    boolean canPlace(Type type, int column, int row);
    ISquare place(Type type, int column, int row);
    void surrender();
    void resetPlayer();
    void nextTurn();
    void onEnemyPieceMove(IPlayer player, ISquare origin, ISquare destination, int pieces);
    void onOwnPieceMove(ISquare origin, ISquare destination, int pieces);
    void onEnemyPiecePlace(IPlayer player, Type type, ISquare square);
    void onOwnPiecePlace(Type type, ISquare square);
    void onEnemyTurnEnd(IPlayer player);
    void onOwnTurnEnd();
    void onGameEnd(@Nullable IPlayer winner);

}
