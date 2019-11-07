package com.github.drsmugleaf.pokemon2.generations.ii.game;

import com.github.drsmugleaf.pokemon2.base.game.IGame;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public enum GamesII implements IGame {

    GOLD_AND_SILVER("Gold and Silver", true),
    CRYSTAL("Crystal", true);

    private final String NAME;
    private final boolean CORE;

    GamesII(String name, boolean core) {
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
