package com.github.drsmugleaf.tak.bot.minimax;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.Player;
import com.github.drsmugleaf.tak.player.PlayerInformation;

import java.util.List;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class MinimaxFlatBot extends MinimaxBot {

    protected MinimaxFlatBot(String name, Game game, Color color, Preset preset, int depth) {
        super(name, game, color, preset, depth);
    }

    public static Player from(PlayerInformation information, int depth) {
        return new MinimaxFlatBot(information.NAME, information.GAME, information.COLOR, information.PRESET, depth);
    }

    public static Function<PlayerInformation, Player> from(int depth) {
        return information -> from(information, depth);
    }

    public static Player from(PlayerInformation information) {
        return from(information, 3);
    }

    @Override
    public List<Coordinates> getAvailableActions(Board board) {
        return super.getAvailableActions(board, Type.FLAT_STONE);
    }

}
