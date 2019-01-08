package com.github.drsmugleaf.chess.player;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 08/01/2019
 */
public class Player {

    @NotNull
    private final String NAME;

    public Player(@NotNull String name) {
        NAME = name;
    }

    @NotNull
    public String getName() {
        return NAME;
    }

}
