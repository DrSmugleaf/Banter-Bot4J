package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public enum Stat {

    HP("Health", "HP") {
        @Override
        public int calculate(Pokemon pokemon, Stat stat) {
            int iv = pokemon.getIndividualValue(Stat.HP);
            int baseStat = pokemon.getBaseStat(Stat.HP);
            int ev = pokemon.getEffortValue(Stat.HP);
            int level = pokemon.getLevel();

            return ((iv + 2 * baseStat + (ev / 4)) * level / 100) + 10 + level;
        }
    },
    ATTACK("Attack", "Atk"),
    DEFENSE("Defense", "Def"),
    SPECIAL_ATTACK("Special Attack", "SpA"),
    SPECIAL_DEFENSE("Special Defense", "SpD"),
    SPEED("Speed", "Spe"),
    ACCURACY("Accuracy", "Acc") {
        @Override
        public int calculate(Pokemon pokemon, Stat stat) {
            return pokemon.getBaseStat(Stat.ACCURACY);
        }
    },
    EVASION("Evasion", "Eva") {
        @Override
        public int calculate(Pokemon pokemon, Stat stat) {
            return pokemon.getBaseStat(Stat.EVASION);
        }
    };

    private final String NAME;
    private final String ABBREVIATION;

    Stat(@Nonnull String name, @Nonnull String abbreviation) {
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

    public int calculate(Pokemon pokemon, Stat stat) {
        int iv = pokemon.getIndividualValue(stat);
        int baseStat = pokemon.getBaseStat(stat);
        int ev = pokemon.getEffortValue(stat);
        int level = pokemon.getLevel();
        double natureMultiplier = pokemon.getNature().getNatureMultiplier(stat);
        double stageMultiplier = pokemon.getStatStageMultiplier(stat);
        return (int) ((((iv + 2 * baseStat + (ev / 4)) * level / 100) + 5) * natureMultiplier * stageMultiplier);
    }

    public int calculateWithoutStages(Pokemon pokemon, Stat stat) {
        int iv = pokemon.getIndividualValue(stat);
        int baseStat = pokemon.getBaseStat(stat);
        int ev = pokemon.getEffortValue(stat);
        int level = pokemon.getLevel();
        double natureMultiplier = pokemon.getNature().getNatureMultiplier(stat);
        return (int) ((((iv + 2 * baseStat + (ev / 4)) * level / 100) + 5) * natureMultiplier);
    }

}
