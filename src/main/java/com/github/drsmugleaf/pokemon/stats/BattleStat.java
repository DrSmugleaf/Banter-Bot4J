package com.github.drsmugleaf.pokemon.stats;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 14/09/2017.
 */
public enum BattleStat implements IStat {

    ACCURACY("Accuracy", "Acc"),
    EVASION("Evasion", "Eva");

    public final String NAME;

    public final String ABBREVIATION;

    BattleStat(String name, String abbreviation) {
        NAME = name;
        ABBREVIATION = abbreviation;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getAbbreviation() {
        return ABBREVIATION;
    }

    @Override
    public double calculate(Pokemon pokemon) {
        return pokemon.STATS.get(this).getStage().getStatMultiplier(this);
    }

    @Override
    public double calculateWithoutStages(Pokemon pokemon) {
        return 1.0;
    }

}
