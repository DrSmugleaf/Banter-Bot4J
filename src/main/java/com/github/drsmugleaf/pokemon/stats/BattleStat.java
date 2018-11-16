package com.github.drsmugleaf.pokemon.stats;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 14/09/2017.
 */
public enum BattleStat implements IStat {

    ACCURACY("Accuracy", "Acc"),
    EVASION("Evasion", "Eva");

    @NotNull
    public final String NAME;

    @NotNull
    public final String ABBREVIATION;

    BattleStat(@NotNull String name, @NotNull String abbreviation) {
        NAME = name;
        ABBREVIATION = abbreviation;
    }

    @NotNull
    @Override
    public String getName() {
        return NAME;
    }

    @NotNull
    @Override
    public String getAbbreviation() {
        return ABBREVIATION;
    }

    @Override
    public double calculate(@NotNull Pokemon pokemon) {
        return pokemon.STATS.get(this).getStage().getStatMultiplier(this);
    }

    @Override
    public double calculateWithoutStages(@NotNull Pokemon pokemon) {
        return 1.0;
    }

}
