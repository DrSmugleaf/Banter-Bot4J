package com.github.drsmugleaf.tak.bot.minimax;

import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;

import java.util.List;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 26/01/2019
 */
public class MinimaxFlatMoveBot extends MinimaxBot {

    protected MinimaxFlatMoveBot(String name, IGame game, IColor color, int depth) {
        super(name, game, color, depth);
    }

    public static IPlayer from(IPlayerInformation information, int depth) {
        return new MinimaxFlatMoveBot(information.getName(), information.getGame(), information.getColor(), depth);
    }

    public static Function<IPlayerInformation, IPlayer> from(int depth) {
        return information -> from(information, depth);
    }

    public static IPlayer from(IPlayerInformation information) {
        return from(information, 3);
    }

    @Override
    public List<IAction> getAvailableActions(IBoard board) {
        return getAvailableActions(board, Type.FLAT_STONE);
    }

}
