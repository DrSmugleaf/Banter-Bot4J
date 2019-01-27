package com.github.drsmugleaf.tak.bot;

import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by DrSmugleaf on 30/12/2018
 */
public abstract class Bot extends Player {

    protected Bot(@NotNull String name, @NotNull Game game, @NotNull Color color, @NotNull Preset preset) {
        super(name, game, color, preset);
    }

    @Nullable
    public abstract Coordinates getNextMove();

    @Override
    public final void nextTurn() {
        Coordinates move = getNextMove();
        if (move == null) {
            surrender();
            return;
        }

        move.place(this);
    }

}
