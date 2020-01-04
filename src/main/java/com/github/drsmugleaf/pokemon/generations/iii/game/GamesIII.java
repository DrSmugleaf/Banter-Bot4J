package com.github.drsmugleaf.pokemon.generations.iii.game;

import com.github.drsmugleaf.pokemon.base.game.IGame;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public enum GamesIII implements IGame {

    RUBY_AND_SAPPHIRE("Ruby and Sapphire", true),
    FIRERED_AND_LEAFGREEN("FireRed and LeafGreen", true),
    EMERALD("Emerald", true);

    private final String NAME;
    private final boolean CORE;

    GamesIII(String name, boolean core) {
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
