package com.github.drsmugleaf.tak.game;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.IPreset;
import com.github.drsmugleaf.tak.board.ISquare;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IPiece;
import com.github.drsmugleaf.tak.pieces.IType;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Game implements IGame {

    private final IBoard BOARD;
    private final ImmutableMap<IColor, IPlayer> PLAYERS;
    public IPlayer nextPlayer;
    @Nullable
    private IPlayer winner = null;
    private boolean active = true;

    protected Game(IBoard board) {
        BOARD = board;
        PLAYERS = ImmutableMap.of();
    }

    public Game(
            IBoard board,
            String playerName1,
            String playerName2,
            Function<IPlayerInformation, IPlayer> playerMaker1,
            Function<IPlayerInformation, IPlayer> playerMaker2
    ) {
        BOARD = board;
        IPlayer player1 = playerMaker1.apply(new PlayerInformation(playerName1, this, Color.BLACK));
        IPlayer player2 = playerMaker2.apply(new PlayerInformation(playerName2, this, Color.WHITE));
        PLAYERS = ImmutableMap.of(player1.getColor(), player1, player2.getColor(), player2);
        nextPlayer = player1;
    }

    public static IGame from(
            IPreset preset,
            String playerName1,
            String playerName2,
            Function<IPlayerInformation, IPlayer> playerMaker1,
            Function<IPlayerInformation, IPlayer> playerMaker2
    ) {
        IBoard board = new Board(preset);
        return new Game(board, playerName1, playerName2, playerMaker1, playerMaker2);
    }

    @Override
    public IBoard getBoard() {
        return BOARD;
    }

    @Override
    public IPlayer getPlayer(IColor color) {
        return getPlayers().get(color);
    }

    @Override
    public ImmutableMap<IColor, IPlayer> getPlayers() {
        return PLAYERS;
    }

    @Override
    public boolean canMove(IPlayer player, ISquare origin, ISquare destination, int pieces) {
        return isActive() && nextPlayer == player && getBoard().canMove(origin, destination, pieces);
    }

    @Override
    public ISquare move(IPlayer player, ISquare origin, ISquare destination, int pieces) {
        if (!canMove(player, origin, destination, pieces)) {
            throw new IllegalGameCall("Illegal move call, origin " + origin + ", destination " + destination + " and pieces " + pieces);
        }

        ISquare square = getBoard().move(origin, destination, pieces);
        onPieceMove(player, origin, destination, pieces);
        return square;
    }

    @Override
    public boolean canPlace(IPlayer player, int row, int column) {
        return isActive() && nextPlayer == player && getBoard().canPlace(row, column);
    }

    @Override
    public ISquare place(IPlayer player, IType type, int row, int column) {
        if (!canPlace(player, row, column)) {
            throw new IllegalGameCall("Illegal place call, piece type " + type + " at row " + row + " and column " + column);
        }

        IPiece piece = player.getHand().takePiece(type);
        ISquare square = getBoard().place(piece, row, column);
        onPiecePlace(player, type, square);
        return square;
    }

    @Nullable
    @Override
    public IPlayer checkVictory() {
        boolean blackWins = getBoard().hasRoad(Color.BLACK);
        boolean whiteWins = getBoard().hasRoad(Color.WHITE);
        if (blackWins && whiteWins) {
            return nextPlayer;
        }

        IColor winningColor;
        if (blackWins) {
            winningColor = Color.BLACK;
        } else if (whiteWins) {
            winningColor = Color.WHITE;
        } else {
            return null;
        }

        IPlayer winner = getPlayers().get(winningColor);
        setWinner(winner);
        return getWinner();
    }

    @Nullable
    @Override
    public IPlayer forceVictory() {
        Map<IColor, Integer> flatStonesByColor = new HashMap<>();
        for (IColor color : Color.getColors()) {
            int stones = getBoard().countFlat(color);
            flatStonesByColor.put(color, stones);
        }

        Map.Entry<IColor, Integer> maximum = null;
        for (Map.Entry<IColor, Integer> entry : flatStonesByColor.entrySet()) {
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

    @Override
    public IPlayer getNextPlayer() {
        return nextPlayer;
    }

    @Override
    public IPlayer getOtherPlayer(IPlayer player) {
        return getPlayers().get(player.getColor().getOpposite());
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void end() {
        active = false;
        onGameEnd(getWinner());
    }

    @Nullable
    @Override
    public IPlayer getWinner() {
        return winner;
    }

    @Override
    public void setWinner(@Nullable IPlayer winner) {
        this.winner = winner;
    }

    @Override
    public void surrender(IPlayer loser) {
        if (!isActive()) {
            throw new IllegalGameCall("Game already ended");
        }

        setWinner(getOtherPlayer(loser));
        end();
    }

    @Nullable
    @Override
    public IPlayer start() {
        while (isActive()) {
            nextTurn();
        }

        return getWinner();
    }

    @Override
    public void nextTurn() {
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

    @Override
    public void reset() {
        for (IPlayer player : getPlayers().values()) {
            player.resetPlayer();
        }

        getBoard().reset();
        nextPlayer = getPlayer(Color.BLACK);
        winner = null;
        active = true;
    }

    @Override
    public void onPieceMove(IPlayer player, ISquare origin, ISquare destination, int pieces) {
        player.onOwnPieceMove(origin, destination, pieces);
        getOtherPlayer(player).onEnemyPieceMove(player, origin, destination, pieces);
    }

    @Override
    public void onPiecePlace(IPlayer player, IType type, ISquare square) {
        player.onOwnPiecePlace(type, square);
        getOtherPlayer(player).onEnemyPiecePlace(player, type, square);
    }

    @Override
    public void onTurnEnd(IPlayer player) {
        player.onOwnTurnEnd();
        getOtherPlayer(player).onEnemyTurnEnd(player);
    }

    @Override
    public void onGameEnd(@Nullable IPlayer winner) {
        for (IPlayer player : getPlayers().values()) {
            player.onGameEnd(winner);
        }
    }

}
