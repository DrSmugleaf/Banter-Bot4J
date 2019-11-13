package com.github.drsmugleaf.pokemon2.generations.viii.game;

import com.github.drsmugleaf.pokemon2.base.game.IGame;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum GamesVIII implements IGame {

    SWORD_AND_SHIELD("Sword and Shield", true);

    private final String NAME;
    private final boolean CORE;

    GamesVIII(String name, boolean core) {
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
