package com.github.drsmugleaf.pokemon2.generations.v.game;

import com.github.drsmugleaf.pokemon2.base.game.IGame;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public enum GamesV implements IGame {

    BLACK_AND_WHITE("Black and White", true),
    BLACK_2_AND_WHITE_2("Black 2 and White 2", true);

    private final String NAME;
    private final boolean CORE;

    GamesV(String name, boolean core) {
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
