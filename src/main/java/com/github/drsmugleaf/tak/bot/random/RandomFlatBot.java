package com.github.drsmugleaf.tak.bot.random;

import com.github.drsmugleaf.tak.board.IAction;
import com.github.drsmugleaf.tak.board.IPlace;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.board.IBoard;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.Type;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 25/12/2018
 */
public class RandomFlatBot extends RandomBot {

    protected RandomFlatBot(String name, IGame game, IColor color) {
        super(name, game, color);
    }

    public static IPlayer from(IPlayerInformation information) {
        return new RandomFlatBot(information.getName(), information.getGame(), information.getColor());
    }

    @Override
    public List<IAction> getAvailableActions(IBoard board) {
        List<IPlace> places = getAvailablePlaces(board, Type.FLAT_STONE);
        return new ArrayList<>(places);
    }

}
