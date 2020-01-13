package com.github.drsmugleaf.tak.game;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.layout.ISquare;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IType;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.google.common.collect.ImmutableMap;

/**
 * Created by DrSmugleaf on 08/01/2020
 */
public interface IGame {

    IBoard getBoard();
    IPlayer getPlayer(IColor color);
    ImmutableMap<IColor, IPlayer> getPlayers();
    boolean canMove(IPlayer player, ISquare origin, ISquare destination, int pieces);
    ISquare move(IPlayer player, ISquare origin, ISquare destination, int pieces);
    boolean canPlace(IPlayer player, int row, int column);
    ISquare place(IPlayer player, IType type, int row, int column);
    IPlayer checkVictory();
    IPlayer forceVictory();
    IPlayer getNextPlayer();
    IPlayer getOtherPlayer(IPlayer player);
    boolean isActive();
    void end();
    @Nullable
    IPlayer getWinner();
    void setWinner(@Nullable IPlayer winner);
    void surrender(IPlayer loser);
    @Nullable
    IPlayer start();
    void nextTurn();
    void reset();
    void onPieceMove(IPlayer player, ISquare origin, ISquare destination, int pieces);
    void onPiecePlace(IPlayer player, IType type, ISquare square);
    void onTurnEnd(IPlayer player);
    void onGameEnd(@Nullable IPlayer winner);

}
