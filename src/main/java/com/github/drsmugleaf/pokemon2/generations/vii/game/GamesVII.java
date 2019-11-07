package com.github.drsmugleaf.pokemon2.generations.vii.game;

import com.github.drsmugleaf.pokemon2.base.game.IGame;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public enum GamesVII implements IGame {

    SUN_AND_MOON("Sun and Moon", true),
    ULTRA_SUN_AND_ULTRA_MOON("Ultra Sun and Ultra Moon", true);

    private final String NAME;
    private final boolean CORE;

    GamesVII(String name, boolean core) {
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
