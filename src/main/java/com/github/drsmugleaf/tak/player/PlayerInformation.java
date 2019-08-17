package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class PlayerInformation<T extends Game> {

    public final String NAME;
    public final T GAME;
    public final Color COLOR;
    public final Preset PRESET;

    public PlayerInformation(String name, T game, Color color, Preset preset) {
        NAME = name;
        GAME = game;
        COLOR = color;
        PRESET = preset;
    }

}
