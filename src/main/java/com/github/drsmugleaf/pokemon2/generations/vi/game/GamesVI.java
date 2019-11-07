package com.github.drsmugleaf.pokemon2.generations.vi.game;

import com.github.drsmugleaf.pokemon2.base.game.IGame;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public enum GamesVI implements IGame {

    X_AND_Y("X and Y", true),
    OMEGA_RUBY_AND_ALPHA_SAPPHIRE("Omega Ruby and Alpha Sapphire", true);

    private final String NAME;
    private final boolean CORE;

    GamesVI(String name, boolean core) {
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
