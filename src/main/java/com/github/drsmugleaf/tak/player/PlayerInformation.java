package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.game.Game;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class PlayerInformation {

    public final String NAME;
    public final Game GAME;
    public final Color COLOR;
    public final Preset PRESET;

    public PlayerInformation(String name, Game game, Color color, Preset preset) {
        NAME = name;
        GAME = game;
        COLOR = color;
        PRESET = preset;
    }

}
