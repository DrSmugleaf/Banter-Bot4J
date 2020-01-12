package com.github.drsmugleaf.tak.bot.random;

import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.player.IPlayer;
import com.github.drsmugleaf.tak.player.IPlayerInformation;

/**
 * Created by DrSmugleaf on 10/01/2020
 */
public class RandomAllBot extends RandomBot {

    protected RandomAllBot(String name, IGame game, IColor color) {
        super(name, game, color);
    }

    public static IPlayer from(IPlayerInformation information) {
        return new RandomAllBot(information.getName(), information.getGame(), information.getColor());
    }

}
