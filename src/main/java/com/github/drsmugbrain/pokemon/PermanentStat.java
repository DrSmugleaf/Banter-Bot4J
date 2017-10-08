package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 04/06/2017.
 */
public enum PermanentStat implements IStat {

    HP("Health", "HP") {
        @Override
        public int calculate(Pokemon pokemon, PermanentStat stat, Generation generation, Double stageMultiplier) {
            int baseStat = pokemon.getBaseStat(stat);
            int iv = pokemon.getIndividualValue(stat);
            int ev = pokemon.getEffortValue(stat);
            int level = pokemon.getLevel();
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
        public int calculateWithoutStages(Pokemon pokemon, PermanentStat stat, Generation generation) {
            return this.calculate(pokemon, stat, generation, 1.0);
        }
    },
    ATTACK("Attack", "Atk"),
    DEFENSE("Defense", "Def"),
    SPECIAL_ATTACK("Special Attack", "SpA"),
    SPECIAL_DEFENSE("Special Defense", "SpD"),
    SPEED("Speed", "Spe");

    private final String NAME;
    private final String ABBREVIATION;

    PermanentStat(@Nonnull String name, @Nonnull String abbreviation) {
        this.NAME = name;
        this.ABBREVIATION = abbreviation;
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

    public int calculate(Pokemon pokemon, PermanentStat stat, Generation generation, Double stageMultiplier) {
        int baseStat = pokemon.getBaseStat(stat);
        int iv = pokemon.getIndividualValue(stat);
        int ev = pokemon.getEffortValue(stat);
        int level = pokemon.getLevel();

        switch (generation) {
            case I:
            case II:
                return (int) ((int) (((((baseStat + iv) * 2 + (Math.sqrt(ev) / 4)) * level) / 100) + 5) * stageMultiplier);
            case III:
            case IV:
            case V:
            case VI:
            case VII:
                double natureMultiplier = pokemon.getNature().getNatureMultiplier(stat);
                return (int) ((int) (((int) ((((2.0 * baseStat + iv + (ev / 4.0)) * level) / 100.0) + 5.0)) * natureMultiplier) * stageMultiplier);
            default:
                throw new InvalidGenerationException(generation);
        }
    }

    public int calculateWithoutStages(Pokemon pokemon, PermanentStat stat, Generation generation) {
        return this.calculate(pokemon, stat, generation, 1.0);
    }

}
