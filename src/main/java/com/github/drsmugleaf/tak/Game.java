package com.github.drsmugleaf.tak;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.ISquare;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Game<T extends Board> {

    private final T BOARD;
    private final Map<Color, Player> PLAYERS = new EnumMap<>(Color.class);
    public Player nextPlayer;
    @Nullable
    private Player winner = null;
    private boolean active = true;

    public Game(
            T board,
            String playerName1,
            String playerName2,
            Function<PlayerInformation, Player> playerMaker1,
            Function<PlayerInformation, Player> playerMaker2
    ) {
        BOARD = board;
        Player player1 = playerMaker1.apply(new PlayerInformation<>(playerName1, this, Color.BLACK, BOARD.getPreset()));
        Player player2 = playerMaker2.apply(new PlayerInformation<>(playerName2, this, Color.WHITE, BOARD.getPreset()));
        PLAYERS.put(player1.getColor(), player1);
        PLAYERS.put(player2.getColor(), player2);
        nextPlayer = player1;
    }

    public static Game from(
            Preset preset,
            String playerName1,
            String playerName2,
            Function<PlayerInformation, Player> playerMaker1,
            Function<PlayerInformation, Player> playerMaker2
    ) {
        Board board = new Board(preset);
        return new Game<>(board, playerName1, playerName2, playerMaker1, playerMaker2);
    }

    public T getBoard() {
        return BOARD;
    }

    public Player getPlayer(Color color) {
        return PLAYERS.get(color);
    }

    public Map<Color, Player> getPlayers() {
        return new EnumMap<>(PLAYERS);
    }

    public boolean canMove(Player player, ISquare origin, ISquare destination, int pieces) {
        return isActive() && nextPlayer == player && BOARD.canMove(origin, destination, pieces);
    }

    public ISquare move(Player player, ISquare origin, ISquare destination, int pieces) {
        if (!canMove(player, origin, destination, pieces)) {
            throw new IllegalGameCall("Illegal move call, origin " + origin + ", destination " + destination + " and pieces " + pieces);
        }

        ISquare square = BOARD.move(origin, destination, pieces);
        onPieceMove(player, origin, destination, pieces);
        return square;
    }

    public boolean canPlace(Player player, int column, int row) {
        return isActive() && nextPlayer == player && BOARD.canPlace(column, row);
    }

    public ISquare place(Player player, Type type, int column, int row) {
        if (!canPlace(player, column, row)) {
            throw new IllegalGameCall("Illegal place call, piece type " + type + " at row " + row + " and column " + column);
        }

        Piece piece = player.getHand().takePiece(type);
        ISquare square = BOARD.place(piece, column, row);
        onPiecePlace(player, type, square);
        return square;
    }

    @Nullable
    protected Player checkVictory() {
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
    protected Player forceVictory() {
        Map<Color, Integer> flatStonesByColor = new EnumMap<>(Color.class);
        for (Color color : Color.getColors()) {
            int stones = BOARD.countFlat(color);
            flatStonesByColor.put(color, stones);
        }

        Map.Entry<Color, Integer> maximum = null;
        for (Map.Entry<Color, Integer> entry : flatStonesByColor.entrySet()) {
            if (maximum == null) {
                maximum = entry;
                continue;
            }

            if (maximum.getValue().equals(entry.getValue())) {
                return null;
            }

            if (maximum.getValue() < entry.getValue()) {
                maximum = entry;
            }
        }

        if (maximum == null) {
            throw new IllegalStateException("No initial maximum entry found");
        }

        return getPlayer(maximum.getKey());
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public Player getOtherPlayer(Player player) {
        return PLAYERS.get(player.getColor().getOpposite());
    }

    public boolean isActive() {
        return active;
    }

    public void end() {
        active = false;
        onGameEnd(getWinner());
    }

    @Nullable
    public Player getWinner() {
        return winner;
    }

    protected void setWinner(@Nullable Player winner) {
        this.winner = winner;
    }

    public void surrender(Player loser) {
        if (!isActive()) {
            throw new IllegalGameCall("Game already ended");
        }

        setWinner(getOtherPlayer(loser));
        end();
    }

    @Nullable
    public Player start() {
        while (isActive()) {
            nextTurn();
        }

        return getWinner();
    }

    public void nextTurn() {
        nextPlayer.nextTurn();

        setWinner(checkVictory());
        if (getWinner() != null) {
            end();
        } else if (!nextPlayer.getHand().hasAny() || BOARD.isFull()) {
            setWinner(forceVictory());
            end();
        }

        nextPlayer = getOtherPlayer(nextPlayer);
        onTurnEnd(getOtherPlayer(nextPlayer));
    }

    public void resetGame() {
        for (Player player : PLAYERS.values()) {
            player.resetPlayer();
        }

        BOARD.reset();
        nextPlayer = getPlayer(Color.BLACK);
        winner = null;
        active = true;
    }

    protected void onPieceMove(Player player, ISquare origin, ISquare destination, int pieces) {
        player.onOwnPieceMove(origin, destination, pieces);
        getOtherPlayer(player).onEnemyPieceMove(player, origin, destination, pieces);
    }

    protected void onPiecePlace(Player player, Type type, ISquare square) {
        player.onOwnPiecePlace(type, square);
        getOtherPlayer(player).onEnemyPiecePlace(player, type, square);
    }

    protected void onTurnEnd(Player player) {
        player.onOwnTurnEnd();
        getOtherPlayer(player).onEnemyTurnEnd(player);
    }

    protected void onGameEnd(@Nullable Player winner) {
        for (Player player : PLAYERS.values()) {
            player.onGameEnd(winner);
        }
    }

}
