package com.github.drsmugleaf.pokemon2.generations.i.battle.variant;

import com.github.drsmugleaf.pokemon2.base.battle.variant.IBattleVariant;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public enum BattleVariants implements IBattleVariant {

    FULL_BATTLE(1, 1, 6, "Full Battle");

    private final int MAX_TRAINERS;
    private final int MAX_ACTIVE_POKEMON;
    private final int MAX_PARTY_SIZE;
    private final String NAME;

    BattleVariants(int maxTrainers, int maxActivePokemon, int maxPartySize, String name) {
        MAX_TRAINERS = maxTrainers;
        MAX_ACTIVE_POKEMON = maxActivePokemon;
        MAX_PARTY_SIZE = maxPartySize;
        NAME = name;
    }

    @Contract(pure = true)
    @Override
    public int getMaxTrainers() {
        return MAX_TRAINERS;
    }

    @Contract(pure = true)
    @Override
    public int getMaxActivePokemon() {
        return MAX_ACTIVE_POKEMON;
    }

    @Contract(pure = true)
    @Override
    public int getMaxPartySize() {
        return MAX_PARTY_SIZE;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
