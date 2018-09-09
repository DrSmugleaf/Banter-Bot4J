package com.github.drsmugleaf.pokemon.stats;

import com.github.drsmugleaf.pokemon.battle.Generation;
import com.github.drsmugleaf.pokemon.battle.InvalidGenerationException;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public enum PermanentStat implements IStat {

    HP("Health", "HP") {
        @Override
        public double calculate(@Nonnull Pokemon pokemon) {
            Stat stat = pokemon.STATS.get(this);

            int baseStat = stat.getBase(pokemon.getSpecies());
            int iv = stat.IV;
            int ev = stat.EV;
            int level = pokemon.getLevel();

            Generation generation = pokemon.getBattle().GENERATION;
            switch (generation) {
                case I:
                case II:
                    return (int) ((((((baseStat + iv) * 2) + (Math.sqrt(ev) / 4)) * level) / 100) + level + 10);
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    return ((((((2 * baseStat) + iv + (ev / 4))) * level) / 100) + level + 10);
                default:
                    throw new InvalidGenerationException(generation);
            }
        }

        @Override
        public double calculateWithoutStages(@Nonnull Pokemon pokemon) {
            return calculate(pokemon);
        }
    },
    ATTACK("Attack", "Atk"),
    DEFENSE("Defense", "Def"),
    SPECIAL_ATTACK("Special Attack", "SpA"),
    SPECIAL_DEFENSE("Special Defense", "SpD"),
    SPEED("Speed", "Spe");

    @Nonnull
    public final String NAME;

    @Nonnull
    public final String ABBREVIATION;

    PermanentStat(@Nonnull String name, @Nonnull String abbreviation) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;
        ABBREVIATION = abbreviation;
    }

    @Nonnull
    public static PermanentStat getStat(@Nonnull String name) {
        name = name.toLowerCase();
        if (!Holder.MAP.containsKey(name)) {
            throw new NullPointerException("PermanentStat " + name + " doesn't exist");
        }

        return Holder.MAP.get(name);
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
        Stat stat = pokemon.STATS.get(this);

        int baseStat = stat.getBase(pokemon.getSpecies());
        int iv = stat.IV;
        int ev = stat.EV;
        int level = pokemon.getLevel();
        double stageMultiplier = stat.getStage().getStatMultiplier(this);

        Generation generation = pokemon.getBattle().GENERATION;
        switch (generation) {
            case I:
            case II:
                return (int) ((int) (((((baseStat + iv) * 2 + (Math.sqrt(ev) / 4)) * level) / 100) + 5) * stageMultiplier);
            case III:
            case IV:
            case V:
            case VI:
            case VII:
                double natureMultiplier = pokemon.getNature().getNatureMultiplier(this);
                return (int) ((int) (((int) ((((2.0 * baseStat + iv + (ev / 4.0)) * level) / 100.0) + 5.0)) * natureMultiplier) * stageMultiplier);
            default:
                throw new InvalidGenerationException(generation);
        }
    }

    @Override
    public double calculateWithoutStages(@Nonnull Pokemon pokemon) {
        Stat stat = pokemon.STATS.get(this);

        int baseStat = stat.getBase(pokemon.getSpecies());
        int iv = stat.IV;
        int ev = stat.EV;
        int level = pokemon.getLevel();
        double stageMultiplier = 1.0;

        Generation generation = pokemon.getBattle().GENERATION;
        switch (generation) {
            case I:
            case II:
                return (int) ((int) (((((baseStat + iv) * 2 + (Math.sqrt(ev) / 4)) * level) / 100) + 5) * stageMultiplier);
            case III:
            case IV:
            case V:
            case VI:
            case VII:
                double natureMultiplier = pokemon.getNature().getNatureMultiplier(this);
                return (int) ((int) (((int) ((((2.0 * baseStat + iv + (ev / 4.0)) * level) / 100.0) + 5.0)) * natureMultiplier) * stageMultiplier);
            default:
                throw new InvalidGenerationException(generation);
        }
    }

    private static class Holder {
        @Nonnull
        static Map<String, PermanentStat> MAP = new HashMap<>();
    }

}
