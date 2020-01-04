package com.github.drsmugleaf.pokemon.generations.iv.game;

import com.github.drsmugleaf.pokemon.base.game.IGame;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public enum GamesIV implements IGame {

    DIAMOND_AND_PEARL("Diamond and Pearl", true),
    PLATINUM("Platinum", true),
    HEARTGOLD_AND_SOULSILVER("HeartGold and SoulSilver", true);

    private final String NAME;
    private final boolean CORE;

    GamesIV(String name, boolean core) {
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
