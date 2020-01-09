package com.github.drsmugleaf.tak.bot;

import com.github.drsmugleaf.tak.game.IGame;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.player.Player;

/**
 * Created by DrSmugleaf on 30/12/2018
 */
public abstract class Bot extends Player {

    protected Bot(String name, IGame game, IColor color, boolean passive) {
        super(name, game, color, passive);
    }

}
