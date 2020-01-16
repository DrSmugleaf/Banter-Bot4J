package com.github.drsmugleaf.tak.bot.minimax;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.board.layout.ISquare;
import com.github.drsmugleaf.tak.board.layout.Row;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.board.*;
import com.github.drsmugleaf.tak.bot.Bot;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IPiece;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;
import javafx.util.Pair;

import java.util.List;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 16/03/2019
 */
public class MinimaxBot extends Bot {

    protected final int DEPTH;

    protected MinimaxBot(String name, IGame game, IColor color, int depth) {
        super(name, game, color, false);
        DEPTH = depth;
    }

    public static IPlayer from(IPlayerInformation information, int depth) {
        return new MinimaxBot(information.getName(), information.getGame(), information.getColor(), depth);
    }

    public static Function<IPlayerInformation, IPlayer> from(int depth) {
        return information -> from(information, depth);
    }

    public static IPlayer from(IPlayerInformation information) {
        return from(information, 3);
    }

    @Nullable
    @Override
    public IAction getNextAction() {
        return getBestPlace().getKey();
    }

    protected Pair<IAction, Integer> getBestPlace() {
        IBoard board = getGame().getBoard();
        IColor nextColor = getGame().getNextPlayer().getColor();

        return getMax(getAvailableActions(), board, nextColor, Integer.MIN_VALUE, Integer.MAX_VALUE, DEPTH);
    }

    private boolean isTerminal(IBoard board) {
        return board.getRoad() != null;
    }

    private int getScore(IBoard board) {
        IColor winner = board.getRoad();
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

        for (Row row : board.getRows()) {
            for (ISquare square : row.getSquares()) {
                IPiece topPiece = square.getTopPiece();
                if (topPiece == null) {
                    continue;
                }

                int adjacentScore = board.getAdjacent(square).getConnections().size() * 10;

                IColor topColor = topPiece.getColor();
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

    protected final Pair<IAction, Integer> getMax(List<IAction> actions, IBoard board, IColor nextPlayer, int alpha, int beta, final int depth) {
        IAction bestAction = null;
        int score = 0;

        for (IAction action : actions) {
            int finalAlpha = alpha;
            int finalBeta = beta;
            score = action.with(board, nextPlayer, copy -> {
                if (depth > 1 && !isTerminal(copy)) {
                    List<IAction> availableActions = getGame().getPlayer(nextPlayer.getOpposite()).getAvailableActions(copy);
                    return getMax(availableActions, copy, nextPlayer.getOpposite(), finalAlpha, finalBeta, depth - 1).getValue();
                } else {
                    return getScore(copy);
                }
            }) - depth;

            if (nextPlayer == getColor()) {
                if (score > alpha) {
                    alpha = score;
                    bestAction = action;
                }
            } else if (nextPlayer.getOpposite() == getColor()) {
                if (score < beta) {
                    beta = score;
                    bestAction = action;
                }
            } else {
                throw new IllegalArgumentException("Unrecognized color: " + nextPlayer);
            }

            if (alpha >= beta) {
                break;
            }
        }

        return new Pair<>(bestAction, score);
    }

}
