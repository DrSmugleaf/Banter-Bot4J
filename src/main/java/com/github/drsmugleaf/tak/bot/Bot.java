package com.github.drsmugleaf.tak.bot;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.Game;
import com.github.drsmugleaf.tak.board.ICoordinates;
import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.player.Player;

/**
 * Created by DrSmugleaf on 30/12/2018
 */
public abstract class Bot extends Player {

    protected Bot(String name, Game game, Color color, Preset preset) {
        super(name, game, color, preset);
    }

    @Nullable
    public abstract ICoordinates getNextAction();

    @Override
    public final void nextTurn() {
        ICoordinates move = getNextAction();
        if (move == null) {
            surrender();
            return;
        }

        move.place(this);
    }

}
