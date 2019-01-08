package com.github.drsmugleaf.chess;

import com.github.drsmugleaf.chess.board.Board;
import com.github.drsmugleaf.chess.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 08/01/2019
 */
public class Game {

    @NotNull
    private final Board BOARD;

    @NotNull
    private final List<Player> PLAYERS;

    public Game(@NotNull String player1, @NotNull String player2) {
        BOARD = new Board();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(player1));
        players.add(new Player(player2));
        PLAYERS = Collections.unmodifiableList(players);
    }

    @NotNull
    public Board getBoard() {
        return BOARD;
    }

    @NotNull
    public List<Player> getPlayers() {
        return PLAYERS;
    }

}
