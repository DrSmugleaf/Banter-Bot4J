package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class PlayerInformation {

    @NotNull
    public final String NAME;

    @NotNull
    public final Game GAME;

    @NotNull
    public final Color COLOR;

    @NotNull
    public final Preset PRESET;

    public PlayerInformation(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset) {
        NAME = name;
        GAME = game;
        COLOR = color;
        PRESET = preset;
    }

}
