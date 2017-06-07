package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public enum Stat {

    HP("Health") {
        @Override
        public int calculate(int baseStat, int iv, int ev, int level, boolean positiveNature) {
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
        public int calculate(int baseStat, int iv, int ev, int level, boolean positiveNature) {
            return baseStat;
        }
    },
    EVASION("Evasion") {
        @Override
        public int calculate(int baseStat, int iv, int ev, int level, boolean positiveNature) {
            return baseStat;
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

    public int calculate(int baseStat, int iv, int ev, int level, boolean positiveNature) {
        return (int) ((((iv + 2 * baseStat + (ev / 4)) * level / 100) + 5) * (positiveNature ? 1.1 : 0.9));
    }

}
