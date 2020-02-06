package com.github.drsmugleaf.tak.game;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.action.IMove;
import com.github.drsmugleaf.tak.board.action.IPlace;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.google.common.collect.ImmutableMap;

/**
 * Created by DrSmugleaf on 08/01/2020
 */
public interface IGame {

    IBoard getBoard();
    IPlayer getPlayer(IColor color);
    ImmutableMap<IColor, IPlayer> getPlayers();
    boolean canMove(IPlayer player, IMove move);
    boolean canMove(IPlayer player, IMove move, IBoard board);
    int move(IPlayer player, IMove move, boolean silent);
    boolean canPlace(IPlayer player, IPlace place);
    boolean canPlace(IPlayer player, IPlace place, IBoard board);
    int place(IPlayer player, IPlace place, boolean silent);
    void restore(int state);
    @Nullable
    IPlayer checkVictory();
    @Nullable
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
    void onPieceMove(IPlayer player, IMove move);
    void onPiecePlace(IPlayer player, IPlace place);
    void onTurnEnd(IPlayer player);
    void onGameEnd(@Nullable IPlayer winner);

}
