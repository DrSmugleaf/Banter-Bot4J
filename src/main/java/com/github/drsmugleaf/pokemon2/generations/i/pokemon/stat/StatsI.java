package com.github.drsmugleaf.pokemon2.generations.i.pokemon.stat;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 09/11/2019
 */
public enum StatsI implements IStat {

    HP("HP", "HP", true) {
        @Override
        public int calculate(IPokemon<?> pokemon) {
            Integer baseStat = pokemon.getSpecies().getStats().get(this);
            int level = pokemon.getLevel();
            return ((((baseStat) * 2) + level) / 100) + level + 10;
        }
    },
    ATTACK("Attack", "Atk", true),
    DEFENSE("Defense", "Def", true),
    SPEED("Speed", "Spd", true),
    SPECIAL("Special", "Spe", true),
    EVASION("Evasion", "Eva", false),
    ACCURACY("Accuracy", "Acc", false);

    private final String NAME;
    private final String ABBREVIATION;
    private final boolean IS_PERMANENT;

    StatsI(String name, String abbreviation, boolean isPermanent) {
        NAME = name;
        ABBREVIATION = abbreviation;
        IS_PERMANENT = isPermanent;
    }

    @Contract(pure = true)
    @Override
    public String getAbbreviation() {
        return ABBREVIATION;
    }

    @Contract(pure = true)
    @Override
    public boolean isPermanent() {
        return IS_PERMANENT;
    }

    @Override
    public int calculate(IPokemon<?> pokemon) {
        Integer baseStat = pokemon.getSpecies().getStats().get(this);
        int level = pokemon.getLevel();
        return (((baseStat) * 2 * level) / 100) + 5;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
