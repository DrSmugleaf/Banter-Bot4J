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

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class MinimaxFlatBot extends Player {

    public MinimaxFlatBot(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset) {
        super(name, game, color, preset);
    }

    @NotNull
    public static Player from(@NotNull PlayerInformation information) {
        return new MinimaxFlatBot(information.NAME, information.GAME, information.COLOR, information.PRESET);
    }

    @Override
    public void nextTurn() {
        Coordinates bestCoordinates = getBestCoordinates(getGame().getBoard(), 3);
        if (!getHand().has(Type.FLAT_STONE) || bestCoordinates == null) {
            surrender();
            return;
        }

        bestCoordinates.place(this, Type.FLAT_STONE);
    }

    private Coordinates getBestCoordinates(@NotNull Board board, int depth) {
        return getMax(board, getGame().getNextPlayer().getHand().getColor(), Integer.MIN_VALUE, Integer.MAX_VALUE, depth).getKey();
    }

    private boolean isTerminal(@NotNull Board board) {
        return board.hasRoad() != null;
    }

    private int getScore(@NotNull Board board) {
        Color winner = board.hasRoad();
        if (winner != null) {
            if (winner == getHand().getColor()) {
                return Integer.MAX_VALUE;
            } else if (winner == getHand().getColor().getOpposite()) {
                return Integer.MIN_VALUE;
            } else {
                throw new IllegalArgumentException("Unrecognized color: " + winner);
            }
        }

        int score = 0;

        for (Row row : board.getRows()) {
            for (Square square : row.getSquares()) {
                Piece topPiece = square.getTopPiece();
                if (topPiece == null) {
                    continue;
                }

                Color topColor = topPiece.getColor();
                if (topColor == getHand().getColor()) {
                    score++;
                } else if (topColor == getHand().getColor().getOpposite()) {
                    score--;
                } else {
                    throw new IllegalArgumentException("Unrecognized piece color: " + topColor);
                }
            }
        }

        score += board.countAdjacent(getHand().getColor()) * 10;
        score -= board.countAdjacent(getHand().getColor().getOpposite()) * 10;

        return score;
    }

    @NotNull
    private Pair<Coordinates, Integer> getMax(@NotNull Board board, @NotNull Color nextPlayer, int alpha, int beta, final int depth) {
        Coordinates bestMove = null;
        int score = 0;

        for (Coordinates coordinates : getAvailableFlatMoves(board)) {
            Piece piece = new Piece(nextPlayer, Type.FLAT_STONE);
            int finalAlpha = alpha;
            int finalBeta = beta;
            score = board.with(piece, coordinates.getRow(), coordinates.getColumn(), copy -> {
                if (depth > 1 && !isTerminal(copy)) {
                    return getMax(copy, nextPlayer.getOpposite(), finalAlpha, finalBeta, depth - 1).getValue();
                } else {
                    return getScore(copy);
                }
            }) - depth;

            if (nextPlayer == getHand().getColor()) {
                if (score > alpha) {
                    alpha = score;
                    bestMove = coordinates;
                }
            } else if (nextPlayer.getOpposite() == getHand().getColor()) {
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
