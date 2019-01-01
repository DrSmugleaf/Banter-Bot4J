package com.github.drsmugleaf.tak;

import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.board.Square;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Game {

    @NotNull
    private final Board BOARD;

    @NotNull
    private final List<Player> PLAYERS = new ArrayList<>();

    @NotNull
    private Player nextPlayer;

    @Nullable
    private Player winner = null;

    public Game(
            @NotNull Preset preset,
            @NotNull String playerName1,
            @NotNull String playerName2,
            @NotNull Function<PlayerInformation, Player> playerMaker1,
            @NotNull Function<PlayerInformation, Player> playerMaker2
    ) {
        BOARD = new Board(preset);
        setup(preset, playerName1, playerName2, playerMaker1, playerMaker2);
    }

    protected void setup(
            @NotNull Preset preset,
            @NotNull String playerName1,
            @NotNull String playerName2,
            @NotNull Function<PlayerInformation, Player> playerMaker1,
            @NotNull Function<PlayerInformation, Player> playerMaker2
    ) {
        Player player1 = playerMaker1.apply(new PlayerInformation(playerName1, this, Color.BLACK, preset));
        Player player2 = playerMaker2.apply(new PlayerInformation(playerName2, this, Color.WHITE, preset));
        PLAYERS.add(player1);
        PLAYERS.add(player2);
        nextPlayer = player1;
    }

    @NotNull
    public Board getBoard() {
        return BOARD;
    }

    @NotNull
    public List<Player> getPlayers() {
        return new ArrayList<>(PLAYERS);
    }

    public boolean canPlace(@NotNull Player player, @NotNull Type type, int row, int column) {
        return nextPlayer == player && BOARD.canPlace(type, row, column);
    }

    @NotNull
    public Square place(@NotNull Player player, @NotNull Type type, int row, int column) {
        if (!canPlace(player, type, row, column)) {
            throw new IllegalStateException("Invalid place call, piece type " + type + " at row " + row + " and column " + column);
        }

        Piece piece = player.getHand().takePiece(type);
        Square square = BOARD.place(piece, row, column);
        nextPlayer = getOtherPlayer(player);
        checkVictory();
        return square;
    }

    @Nullable
    public Player checkVictory() {
        Color winningColor = BOARD.hasRoad();
        if (winningColor == null) {
            return null;
        }

        for (Player player : PLAYERS) {
            if (player.getHand().getColor() == winningColor) {
                winner = player;
                return player;
            }
        }

        throw new IllegalStateException("No player found for winning color " + winningColor);
    }

    @NotNull
    public Player getNextPlayer() {
        return nextPlayer;
    }

    @NotNull
    public Player getOtherPlayer(@NotNull Player player1) {
        for (Player player : PLAYERS) {
            if (player != player1) {
                return player;
            }
        }

        throw new IllegalStateException("No other player found");
    }

    public boolean isActive() {
        return winner == null;
    }

    public void surrender(@NotNull Player loser) {
        if (winner != null) {
            throw new IllegalStateException("Game already ended");
        }

        winner = getOtherPlayer(loser);
    }

    public void start() {
        while (isActive()) {
            Player nextPlayer = getNextPlayer();
            if (!nextPlayer.getHand().hasAny()) {
                nextPlayer.surrender();
            }

            nextPlayer.nextTurn();
        }
    }

}
