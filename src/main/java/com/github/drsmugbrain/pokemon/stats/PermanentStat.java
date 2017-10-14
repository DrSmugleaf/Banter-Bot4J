package com.github.drsmugbrain.pokemon.stats;

import com.github.drsmugbrain.pokemon.battle.Generation;
import com.github.drsmugbrain.pokemon.battle.InvalidGenerationException;
import com.github.drsmugbrain.pokemon.pokemon.Pokemon;

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

            int baseStat = stat.getBase(pokemon.getBasePokemon());
            int iv = stat.getIV();
            int ev = stat.getEV();
            int level = pokemon.getLevel();

            Generation generation = pokemon.getBattle().getGeneration();
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
            return this.calculate(pokemon);
        }
    },
    ATTACK("Attack", "Atk"),
    DEFENSE("Defense", "Def"),
    SPECIAL_ATTACK("Special Attack", "SpA"),
    SPECIAL_DEFENSE("Special Defense", "SpD"),
    SPEED("Speed", "Spe");

    @Nonnull
    private final String NAME;

    @Nonnull
    private final String ABBREVIATION;

    PermanentStat(@Nonnull String name, @Nonnull String abbreviation) {
        Holder.MAP.put(name.toLowerCase(), this);
        this.NAME = name;
        this.ABBREVIATION = abbreviation;
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
        return this.NAME;
    }

    @Nonnull
    @Override
    public String getAbbreviation() {
        return this.ABBREVIATION;
    }

    @Override
    public double calculate(@Nonnull Pokemon pokemon) {
        Stat stat = pokemon.STATS.get(this);

        int baseStat = stat.getBase(pokemon.getBasePokemon());
        int iv = stat.getIV();
        int ev = stat.getEV();
        int level = pokemon.getLevel();
        double stageMultiplier = stat.getStage().getStage();

        Generation generation = pokemon.getBattle().getGeneration();
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

        int baseStat = stat.getBase(pokemon.getBasePokemon());
        int iv = stat.getIV();
        int ev = stat.getEV();
        int level = pokemon.getLevel();
        double stageMultiplier = 1.0;

        Generation generation = pokemon.getBattle().getGeneration();
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
        static Map<String, PermanentStat> MAP = new HashMap<>();
    }

}
