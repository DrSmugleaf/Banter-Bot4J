package com.github.drsmugleaf.tak;

import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.board.Square;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Game {

    @NotNull
    private final Board BOARD;

    @NotNull
    private final List<Player> PLAYERS = new ArrayList<>();

    public Game(@NotNull Preset preset, @NotNull String playerName1, @NotNull String playerName2) {
        BOARD = new Board(preset);

        Player player1 = new Player(playerName1, Color.BLACK, preset);
        Player player2 = new Player(playerName2, Color.WHITE, preset);
        PLAYERS.add(player1);
        PLAYERS.add(player2);
    }

    @NotNull
    public List<Player> getPlayers() {
        return new ArrayList<>(PLAYERS);
    }

    public boolean canPlace(@NotNull Player player, @NotNull Type type, int row, int square) {
        return PLAYERS.contains(player) && player.getHand().has(type) && BOARD.canPlace(type, row, square);
    }

    @NotNull
    public Square place(@NotNull Player player, @NotNull Type type, int row, int column) {
        if (!canPlace(player, type, row, column)) {
            throw new IllegalStateException("Invalid place call");
        }

        Piece piece = player.getHand().takePiece(type);
        return BOARD.place(piece, row, column);
    }

    @Nullable
    public Player checkVictory() {
        Color winningColor = BOARD.hasRoad();
        if (winningColor == null) {
            return null;
        }

        for (Player player : PLAYERS) {
            if (player.getHand().getColor() == winningColor) {
                return player;
            }
        }

        throw new IllegalStateException("No player found for winning color " + winningColor);
    }

}
