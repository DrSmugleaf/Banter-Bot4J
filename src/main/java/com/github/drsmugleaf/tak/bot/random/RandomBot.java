package com.github.drsmugleaf.tak.bot.random;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.action.IAction;
import com.github.drsmugleaf.tak.bot.Bot;
import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 16/03/2019
 */
public class RandomBot extends Bot {

    protected RandomBot(String name, IGame game, IColor color) {
        super(name, game, color, false);
    }

    public static IPlayer from(IPlayerInformation information) {
        return new RandomBot(information.getName(), information.getGame(), information.getColor());
    }

    @Nullable
    @Override
    public IAction getNextAction() {
        List<IAction> availableActions = getAvailableActions();
        if (availableActions.isEmpty()) {
            return null;
        }

        int random = ThreadLocalRandom.current().nextInt(availableActions.size());
        return availableActions.get(random);
    }

}
