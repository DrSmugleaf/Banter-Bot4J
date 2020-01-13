package com.github.drsmugleaf.tak.bot.random;

import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;

import java.util.List;

/**
 * Created by DrSmugleaf on 05/01/2019
 */
public class RandomFlatMoveBot extends RandomBot {

    protected RandomFlatMoveBot(String name, IGame game, IColor color) {
        super(name, game, color);
    }

    public static IPlayer from(IPlayerInformation information) {
        return new RandomFlatMoveBot(information.getName(), information.getGame(), information.getColor());
    }

    @Override
    public List<IAction> getAvailableActions(IBoard board) {
        return getAvailableActions(board, Type.FLAT_STONE);
    }

}
