package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 14/09/2017.
 */
public enum BattleStat implements IStat {

    ACCURACY("Accuracy", "Acc"),
    EVASION("Evasion", "Eva");

    private final String NAME;
    private final String ABBREVIATION;

    BattleStat(@Nonnull String name, @Nonnull String abbreviation) {
        this.NAME = name;
        this.ABBREVIATION = abbreviation;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public String getAbbreviation() {
        return this.ABBREVIATION;
    }

    public double calculate(Pokemon pokemon, Stat stat) {
        return pokemon.getStatStage(stat).getStatMultiplier(stat);
    }

    public double calculateWithoutStages(Pokemon pokemon, Stat stat) {
        return Stage.ZERO.getStatMultiplier(stat);
    }

}
