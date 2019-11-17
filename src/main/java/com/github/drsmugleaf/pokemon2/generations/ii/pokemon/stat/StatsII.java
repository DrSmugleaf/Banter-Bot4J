package com.github.drsmugleaf.pokemon2.generations.ii.pokemon.stat;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.base.IBaseStat;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 09/11/2019
 */
public enum StatsII implements IBaseStat<IPokemon<?>> {

    HP("HP", "HP", true) {
        @Override
        public int calculate(IPokemon<?> pokemon) {
            Integer baseStat = pokemon.getSpeciesStats().get(this);
            int level = pokemon.getLevel();
            IStat hpStat = pokemon.getStats().get(this);
            int iv = hpStat.getIndividualValue();
            int ev = hpStat.getEffortValue();
            return (int) ((((((baseStat + iv) * 2) + (Math.sqrt(ev) / 4)) * level) / 100) + level + 10);
        }
    },
    ATTACK("Attack", "Atk", true),
    DEFENSE("Defense", "Def", true),
    SPEED("Speed", "Spd", true),
    SPECIAL_ATTACK("Special Attack", "SpA", true),
    SPECIAL_DEFENSE("Special Defense", "SpD", true),
    EVASION("Evasion", "Eva", false),
    ACCURACY("Accuracy", "Acc", false);

    private final String NAME;
    private final String ABBREVIATION;
    private final boolean IS_PERMANENT;

    StatsII(String name, String abbreviation, boolean isPermanent) {
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
        Integer baseStat = pokemon.getSpeciesStats().get(this);
        int level = pokemon.getLevel();
        IStat stat = pokemon.getStats().get(this);
        int iv = stat.getIndividualValue();
        int ev = stat.getEffortValue();
        double stageMultiplier = stat.getStage().getStatMultiplier(stat);
        return (int) ((int) (((((baseStat + iv) * 2 + (Math.sqrt(ev) / 4)) * level) / 100) + 5) * stageMultiplier);
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
