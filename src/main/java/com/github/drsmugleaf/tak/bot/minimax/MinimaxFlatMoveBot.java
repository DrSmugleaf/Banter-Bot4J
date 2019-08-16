package com.github.drsmugleaf.tak.bot.minimax;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 26/01/2019
 */
public class MinimaxFlatMoveBot extends MinimaxBot {

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

    @NotNull
    @Override
    public List<Coordinates> getAvailableActions(@NotNull Board board) {
        return getAvailableActions(board, Type.FLAT_STONE);
    }

}