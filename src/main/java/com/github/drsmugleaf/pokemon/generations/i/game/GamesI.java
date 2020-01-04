package com.github.drsmugleaf.pokemon.generations.i.game;

import com.github.drsmugleaf.pokemon.base.game.IGame;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public enum GamesI implements IGame {

    RED_AND_GREEN("Red and Green", true),
    BLUE("Blue", true),
    RED_AND_BLUE("Red and Blue", true),
    YELLOW("Yellow", true),
    STADIUM("Stadium", false);

    private final String NAME;
    private final boolean CORE;

    GamesI(String name, boolean core) {
        NAME = name;
        CORE = core;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isCore() {
        return CORE;
    }

}
