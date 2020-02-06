package com.github.drsmugleaf.tak.bot.minimax;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.board.layout.ISquare;
import com.github.drsmugleaf.tak.board.layout.Row;
import com.github.drsmugleaf.tak.bot.Bot;
import com.github.drsmugleaf.tak.game.IGame;
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
        Pair<IAction, Integer> bestAction = getBestAction();
        System.out.println(bestAction.getValue());
        return bestAction.getKey();
    }

    protected Pair<IAction, Integer> getBestAction() {
        IColor nextColor = getGame().getNextPlayer().getColor();
        return getMax(nextColor, Integer.MIN_VALUE, Integer.MAX_VALUE, DEPTH);
    }

    protected int getScore(IBoard board, IColor color) {
        IColor winner = board.getRoad();
        if (winner != null) {
            if (winner == color) {
                return Integer.MAX_VALUE;
            } else if (winner == color.getOpposite()) {
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
                if (topColor == color) {
                    score++;
                    score += adjacentScore;
                } else if (topColor == color.getOpposite()) {
                    score--;
                    score -= adjacentScore;
                } else {
                    throw new IllegalArgumentException("Unrecognized piece color: " + topColor);
                }
            }
        }

        return score;
    }

    protected final Pair<IAction, Integer> getMax(IColor nextColor, int alpha, int beta, final int depth) {
        IPlayer nextPlayer = getGame().getPlayer(nextColor);
        IBoard board = nextPlayer.getGame().getBoard();
        List<IAction> actions = nextPlayer.getAvailableActions(board);
        IAction bestAction = null;
        int score;

        if (depth == 0 || actions.isEmpty()) {
            score = getScore(board, getColor());
            return new Pair<>(bestAction, score);
        } else {
            for (IAction action : actions) {
                int previousState = action.execute(nextPlayer, true);
                if (getColor() == nextColor) {
                    Pair<IAction, Integer> max = getMax(getColor().getOpposite(), alpha, beta, depth - 1);
                    score = max.getValue();

                    if (score > alpha) {
                        alpha = score;
                        bestAction = action;
                    }
                } else {
                    Pair<IAction, Integer> max = getMax(getColor(), alpha, beta, depth - 1);
                    score = max.getValue();

                    if (score < beta) {
                        beta = score;
                        bestAction = action;
                    }
                }

                getGame().restore(previousState);

                if (alpha >= beta) {
                    break;
                }
            }

            return new Pair<>(bestAction, (nextColor == getColor()) ? alpha : beta);
        }
    }

}
