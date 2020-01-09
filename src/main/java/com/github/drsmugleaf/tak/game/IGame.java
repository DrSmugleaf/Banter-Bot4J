package com.github.drsmugleaf.tak.game;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.ISquare;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.google.common.collect.ImmutableMap;

/**
 * Created by DrSmugleaf on 08/01/2020
 */
public interface IGame {

    IBoard getBoard();
    IPlayer getPlayer(Color color);
    ImmutableMap<Color, IPlayer> getPlayers();
    boolean canMove(IPlayer player, ISquare origin, ISquare destination, int pieces);
    ISquare move(IPlayer player, ISquare origin, ISquare destination, int pieces);
    boolean canPlace(IPlayer player, int column, int row);
    ISquare place(IPlayer player, Type type, int column, int row);
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
    void onPiecePlace(IPlayer player, Type type, ISquare square);
    void onTurnEnd(IPlayer player);
    void onGameEnd(@Nullable IPlayer winner);

}
