package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public enum Stat {

    HP("Health") {
        @Override
        public int calculate(Pokemon pokemon, Stat stat) {
            int iv = pokemon.getIndividualValue(Stat.HP);
            int baseStat = pokemon.getBaseStat(Stat.HP);
            int ev = pokemon.getEffortValue(Stat.HP);
            int level = pokemon.getLevel();

            return ((iv + 2 * baseStat + (ev / 4)) * level / 100) + 10 + level;
        }
    },
    ATTACK("Attack"),
    DEFENSE("Defense"),
    SPEED("Speed"),
    SPECIAL_ATTACK("Special Attack"),
    SPECIAL_DEFENSE("Special Defense"),
    ACCURACY("Accuracy") {
        @Override
        public int calculate(Pokemon pokemon, Stat stat) {
            return pokemon.getBaseStat(Stat.ACCURACY);
        }
    },
    EVASION("Evasion") {
        @Override
        public int calculate(Pokemon pokemon, Stat stat) {
            return pokemon.getBaseStat(Stat.EVASION);
        }
    };

    private final String NAME;

    Stat(@Nonnull String name) {
        this.NAME = name;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
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

}
