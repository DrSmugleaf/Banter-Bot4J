package com.github.drsmugleaf.tak.bot;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.*;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class MinimaxFlatBot extends Bot {

    protected final int DEPTH;

    protected MinimaxFlatBot(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset, int depth) {
        super(name, game, color, preset);
        DEPTH = depth;
    }

    @NotNull
    public static Player from(@NotNull PlayerInformation information, int depth) {
        return new MinimaxFlatBot(information.NAME, information.GAME, information.COLOR, information.PRESET, depth);
    }

    @NotNull
    public static Function<PlayerInformation, Player> from(int depth) {
        return information -> from(information, depth);
    }

    @NotNull
    public static Player from(@NotNull PlayerInformation information) {
        return from(information, 3);
    }

    @Nullable
    @Override
    public Coordinates getNextMove() {
        if (!getHand().has(Type.FLAT_STONE)) {
            return null;
        }

        return getBestPlace().getKey();
    }

    @NotNull
    protected Pair<Coordinates, Integer> getBestPlace() {
        Board board = getGame().getBoard();
        Color nextColor = getGame().getNextPlayer().getColor();
        List<Coordinates> availablePlaces = getAvailablePlaces(Type.FLAT_STONE);

        return getMax(availablePlaces, board, nextColor, Integer.MIN_VALUE, Integer.MAX_VALUE, DEPTH);
    }

    private boolean isTerminal(@NotNull Board board) {
        return board.hasRoad() != null;
    }

    private int getScore(@NotNull Board board) {
        Color winner = board.hasRoad();
        if (winner != null) {
            if (winner == getColor()) {
                return Integer.MAX_VALUE;
            } else if (winner == getColor().getOpposite()) {
                return Integer.MIN_VALUE;
            } else {
                throw new IllegalArgumentException("Unrecognized color: " + winner);
            }
        }

        int score = 0;

        for (Line row : board.getRows()) {
            for (Square square : row.getSquares()) {
                Piece topPiece = square.getTopPiece();
                if (topPiece == null) {
                    continue;
                }

                int adjacentScore = board.getAdjacent(square).getConnections().size() * 10;

                Color topColor = topPiece.getColor();
                if (topColor == getColor()) {
                    score++;
                    score += adjacentScore;
                } else if (topColor == getColor().getOpposite()) {
                    score--;
                    score -= adjacentScore;
                } else {
                    throw new IllegalArgumentException("Unrecognized piece color: " + topColor);
                }
            }
        }

        return score;
    }

    @NotNull
    protected final Pair<Coordinates, Integer> getMax(@NotNull List<? extends Coordinates> moves, @NotNull Board board, @NotNull Color nextPlayer, int alpha, int beta, final int depth) {
        Coordinates bestMove = null;
        int score = 0;

        for (Coordinates coordinates : moves) {
            int finalAlpha = alpha;
            int finalBeta = beta;
            score = coordinates.with(board, nextPlayer, copy -> {
                if (depth > 1 && !isTerminal(copy)) {
                    return getMax(getAvailablePlaces(copy, Type.FLAT_STONE), copy, nextPlayer.getOpposite(), finalAlpha, finalBeta, depth - 1).getValue();
                } else {
                    return getScore(copy);
                }
            }) - depth;

            if (nextPlayer == getColor()) {
                if (score > alpha) {
                    alpha = score;
                    bestMove = coordinates;
                }
            } else if (nextPlayer.getOpposite() == getColor()) {
                if (score < beta) {
                    beta = score;
                    bestMove = coordinates;
                }
            } else {
                throw new IllegalArgumentException("Unrecognized color: " + nextPlayer);
            }

            if (alpha >= beta) {
                break;
            }
        }

        return new Pair<>(bestMove, score);
    }

}
