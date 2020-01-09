package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.game.Game;
import com.github.drsmugleaf.tak.pieces.Color;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class PlayerInformation implements IPlayerInformation {

    private final String NAME;
    private final Game GAME;
    private final Color COLOR;

    public PlayerInformation(String name, Game game, Color color) {
        NAME = name;
        GAME = game;
        COLOR = color;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Game getGame() {
        return GAME;
    }

    @Override
    public Color getColor() {
        return COLOR;
    }

}
