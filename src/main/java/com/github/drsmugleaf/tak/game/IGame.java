package com.github.drsmugleaf.tak.game;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.ISquare;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.google.common.collect.ImmutableMap;

/**
 * Created by DrSmugleaf on 08/01/2020
 */
public interface IGame {

    IBoard getBoard();
    Player getPlayer(Color color);
    ImmutableMap<Color, Player> getPlayers();
    boolean canMove(Player player, ISquare origin, ISquare destination, int pieces);
    ISquare move(Player player, ISquare origin, ISquare destination, int pieces);
    boolean canPlace(Player player, int column, int row);
    ISquare place(Player player, Type type, int column, int row);
    Player checkVictory();
    Player forceVictory();
    Player getNextPlayer();
    Player getOtherPlayer(Player player);
    boolean isActive();
    void end();
    @Nullable
    Player getWinner();
    void setWinner(@Nullable Player winner);
    void surrender(Player loser);
    @Nullable
    Player start();
    void nextTurn();
    void reset();
    void onPieceMove(Player player, ISquare origin, ISquare destination, int pieces);
    void onPiecePlace(Player player, Type type, ISquare square);
    void onTurnEnd(Player player);
    void onGameEnd(@Nullable Player winner);

}
