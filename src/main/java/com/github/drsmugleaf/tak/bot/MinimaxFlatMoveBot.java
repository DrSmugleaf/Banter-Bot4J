package com.github.drsmugleaf.tak.bot;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.MovingCoordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 26/01/2019
 */
public class MinimaxFlatMoveBot extends MinimaxFlatBot {

    protected MinimaxFlatMoveBot(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset, int depth) {
        super(name, game, color, preset, depth);
    }

    @NotNull
    public static Player from(@NotNull PlayerInformation information, int depth) {
        return new MinimaxFlatMoveBot(information.NAME, information.GAME, information.COLOR, information.PRESET, depth);
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
        List<MovingCoordinates> availableMoves = getAvailableMoves();
        if (availableMoves.isEmpty()) {
            return super.getNextMove();
        }

        Board board = getGame().getBoard();
        Color nextColor = getGame().getNextPlayer().getColor();
        Pair<Coordinates, Integer> move = getMax(availableMoves, board, nextColor, Integer.MIN_VALUE, Integer.MAX_VALUE, DEPTH);
        Integer moveScore = move.getValue();
        Pair<Coordinates, Integer> place = getBestPlace();
        Integer placeScore = place.getValue();

        if (moveScore > placeScore) {
            return move.getKey();
        } else {
            return place.getKey();
        }
    }

}
