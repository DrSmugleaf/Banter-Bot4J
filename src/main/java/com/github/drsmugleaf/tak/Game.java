package com.github.drsmugleaf.tak;

import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Game {

    @NotNull
    private final Board BOARD;

    @NotNull
    private final List<Player> PLAYERS = new ArrayList<>();

    public Game(@NotNull Board board, @NotNull Collection<Player> players) {
        BOARD = board;
        PLAYERS.addAll(players);
    }

    @NotNull
    public List<Player> getPlayers() {
        return new ArrayList<>(PLAYERS);
    }

    public boolean canPlace(@NotNull Player player, @NotNull Type type, int row, int square) {
        return PLAYERS.contains(player) && player.getHand().has(type) && BOARD.canPlace(type, row, square);
    }

    public boolean place(@NotNull Player player, @NotNull Type type, int row, int square) {
        if (!canPlace(player, type, row, square)) {
            return false;
        }

        Piece piece = player.getHand().takePiece(type);
        BOARD.place(piece, row, square);
        return true;
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
