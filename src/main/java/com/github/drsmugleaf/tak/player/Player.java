package com.github.drsmugleaf.tak.player;

import com.github.drsmugleaf.tak.board.Sizes;
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

    public Player(@NotNull String name, @NotNull Color color, @NotNull Sizes size) {
        NAME = name;
        HAND = new Hand(color, size);
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
