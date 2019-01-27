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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Game {

    @NotNull
    private final Board BOARD;

    @NotNull
    private final Map<Color, Player> PLAYERS = new HashMap<>();

    @NotNull
    private Player nextPlayer;

    @Nullable
    private Player winner = null;

    private boolean active = true;

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
        PLAYERS.put(player1.getColor(), player1);
        PLAYERS.put(player2.getColor(), player2);
        nextPlayer = player1;
    }

    @NotNull
    public Board getBoard() {
        return BOARD;
    }

    @NotNull
    public Map<Color, Player> getPlayers() {
        return new HashMap<>(PLAYERS);
    }

    public boolean canMove(@NotNull Player player, @NotNull Square origin, @NotNull Square destination, int pieces) {
        return isActive() && nextPlayer == player && BOARD.canMove(origin, destination, pieces);
    }

    @NotNull
    public Square move(Player player, @NotNull Square origin, @NotNull Square destination, int pieces) {
        if (!canMove(player, origin, destination, pieces)) {
            throw new IllegalStateException("Invalid move call, origin " + origin + ", destination " + destination + " and pieces " + pieces);
        }

        Square square = BOARD.move(origin, destination, pieces);
        onPieceMove(player, origin, destination, pieces);
        return square;
    }

    public boolean canPlace(@NotNull Player player, int column, int row) {
        return isActive() && nextPlayer == player && BOARD.canPlace(column, row);
    }

    @NotNull
    public Square place(@NotNull Player player, @NotNull Type type, int column, int row) {
        if (!canPlace(player, column, row)) {
            throw new IllegalStateException("Invalid place call, piece type " + type + " at row " + row + " and column " + column);
        }

        Piece piece = player.getHand().takePiece(type);
        Square square = BOARD.place(piece, column, row);
        onPiecePlace(player, type, square);
        return square;
    }

    @Nullable
    private Player checkVictory() {
        boolean blackWins = BOARD.hasRoad(Color.BLACK);
        boolean whiteWins = BOARD.hasRoad(Color.WHITE);
        if (blackWins && whiteWins) {
            return nextPlayer;
        }

        Color winningColor;
        if (blackWins) {
            winningColor = Color.BLACK;
        } else if (whiteWins) {
            winningColor = Color.WHITE;
        } else {
            return null;
        }

        Player winner = PLAYERS.get(winningColor);
        setWinner(winner);
        return getWinner();
    }

    @Nullable
    private Player forceVictory() {
        int blackFlat = getBoard().countFlat(Color.BLACK);
        int whiteFlat = getBoard().countFlat(Color.WHITE);

        if (blackFlat > whiteFlat) {
            return PLAYERS.get(Color.BLACK);
        } else if (blackFlat < whiteFlat) {
            return PLAYERS.get(Color.WHITE);
        } else {
            return null;
        }
    }

    @NotNull
    public Player getNextPlayer() {
        return nextPlayer;
    }

    @NotNull
    public Player getOtherPlayer(@NotNull Player player) {
        return PLAYERS.get(player.getColor().getOpposite());
    }

    public boolean isActive() {
        return active;
    }

    public void end() {
        active = false;
    }

    @Nullable
    public Player getWinner() {
        return winner;
    }

    private void setWinner(@Nullable Player winner) {
        this.winner = winner;
    }

    public void surrender(@NotNull Player loser) {
        if (!isActive()) {
            throw new IllegalStateException("Game already ended");
        }

        setWinner(getOtherPlayer(loser));
        end();
    }

    @Nullable
    public Player start() {
        while (isActive()) {
            nextTurn();
        }

        onGameEnd(getWinner());

        return getWinner();
    }

    protected void nextTurn() {
        nextPlayer.nextTurn();

        setWinner(checkVictory());
        if (getWinner() != null) {
            end();
        } else if (!nextPlayer.getHand().hasAny() || getBoard().isFull()) {
            setWinner(forceVictory());
            end();
        }

        nextPlayer = getOtherPlayer(nextPlayer);
        onTurnEnd(getOtherPlayer(nextPlayer));
    }

    protected void onPieceMove(@NotNull Player player, @NotNull Square origin, @NotNull Square destination, int pieces) {}

    protected void onPiecePlace(@NotNull Player player, @NotNull Type type, @NotNull Square square) {}

    protected void onTurnEnd(@NotNull Player player) {}

    protected void onGameEnd(@Nullable Player winner) {}

}
