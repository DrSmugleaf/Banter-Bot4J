package com.github.drsmugleaf.tak.bot.minimax;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.*;
import com.github.drsmugleaf.tak.bot.Bot;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by DrSmugleaf on 16/03/2019
 */
public abstract class MinimaxBot extends Bot {

    protected final int DEPTH;

    protected MinimaxBot(String name, Game game, Color color, Preset preset, int depth) {
        super(name, game, color, preset);
        DEPTH = depth;
    }

    @Nullable
    @Override
    public Coordinates getNextMove() {
        return getBestPlace().getKey();
    }

    protected Pair<Coordinates, Integer> getBestPlace() {
        Board board = getGame().getBoard();
        Color nextColor = getGame().getNextPlayer().getColor();

        return getMax(getAvailableActions(), board, nextColor, Integer.MIN_VALUE, Integer.MAX_VALUE, DEPTH);
    }

    private boolean isTerminal(Board board) {
        return board.hasRoad() != null;
    }

    private int getScore(Board board) {
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

    protected final Pair<Coordinates, Integer> getMax(List<? extends Coordinates> moves, Board board, Color nextPlayer, int alpha, int beta, final int depth) {
        Coordinates bestMove = null;
        int score = 0;

        for (Coordinates coordinates : moves) {
            int finalAlpha = alpha;
            int finalBeta = beta;
            score = coordinates.with(board, nextPlayer, copy -> {
                if (depth > 1 && !isTerminal(copy)) {
                    List<Coordinates> availableActions = getGame().getPlayer(nextPlayer.getOpposite()).getAvailableActions(copy);
                    return getMax(availableActions, copy, nextPlayer.getOpposite(), finalAlpha, finalBeta, depth - 1).getValue();
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
