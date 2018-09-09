package com.github.drsmugleaf.pokemon.stats;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 14/09/2017.
 */
public enum BattleStat implements IStat {

    ACCURACY("Accuracy", "Acc"),
    EVASION("Evasion", "Eva");

    @Nonnull
    public final String NAME;

    @Nonnull
    public final String ABBREVIATION;

    BattleStat(@Nonnull String name, @Nonnull String abbreviation) {
        NAME = name;
        ABBREVIATION = abbreviation;
    }

    @Nonnull
    @Override
    public String getName() {
        return NAME;
    }

    @Nonnull
    @Override
    public String getAbbreviation() {
        return ABBREVIATION;
    }

    @Override
    public double calculate(@Nonnull Pokemon pokemon) {
        return pokemon.STATS.get(this).getStage().getStatMultiplier(this);
    }

    @Override
    public double calculateWithoutStages(@Nonnull Pokemon pokemon) {
        return 1.0;
    }

}
