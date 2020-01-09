package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.game.Game;
import com.github.drsmugleaf.tak.pieces.Color;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class PlayerInformation {

    public final String NAME;
    public final Game GAME;
    public final Color COLOR;

    public PlayerInformation(String name, Game game, Color color) {
        NAME = name;
        GAME = game;
        COLOR = color;
    }

}
