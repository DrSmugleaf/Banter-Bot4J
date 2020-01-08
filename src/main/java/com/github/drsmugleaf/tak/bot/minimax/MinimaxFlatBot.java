package com.github.drsmugleaf.tak.bot.minimax;

import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.board.ICoordinates;
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

    protected MinimaxFlatBot(String name, IGame game, Color color, int depth) {
        super(name, game, color, depth);
    }

    public static Player from(PlayerInformation information, int depth) {
        return new MinimaxFlatBot(information.NAME, information.GAME, information.COLOR, depth);
    }

    public static Function<PlayerInformation, Player> from(int depth) {
        return information -> from(information, depth);
    }

    public static Player from(PlayerInformation information) {
        return from(information, 3);
    }

    @Override
    public List<ICoordinates> getAvailableActions(IBoard board) {
        return super.getAvailableActions(board, Type.FLAT_STONE);
    }

}
