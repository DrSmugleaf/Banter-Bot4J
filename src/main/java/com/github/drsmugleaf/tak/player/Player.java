package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.board.Preset;
import com.github.drsmugleaf.tak.pieces.Color;
import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Player {

    @NotNull
    private final String NAME;

    @NotNull
    private final Hand HAND;

    public Player(@NotNull String name, @NotNull Color color, @NotNull Preset preset) {
        NAME = name;
        HAND = new Hand(color, preset);
    }

    @NotNull
    public String getName() {
        return NAME;
    }

    @NotNull
    public Hand getHand() {
        return HAND;
    }

}
