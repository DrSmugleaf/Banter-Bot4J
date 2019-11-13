package com.github.drsmugleaf.pokemon2.base.species;

import com.github.drsmugleaf.pokemon.pokemon.Gender;
import com.github.drsmugleaf.pokemon2.base.format.IFormat;
import com.github.drsmugleaf.pokemon2.base.species.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.species.type.IType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public abstract class Species<T extends ISpecies<T>> implements ISpecies<T> {

    private final ImmutableSet<String> GENERATIONS;
    private final ImmutableSet<IType> TYPES;
    private final ImmutableSet<IFormat> TIERS;
    private final ImmutableMap<IStat, Integer> STATS;
    private final ImmutableSet<T> EVOLUTIONS;
    private final double HEIGHT;
    private final double WEIGHT;
    private final ImmutableSet<Gender> GENDERS;
    private final ImmutableSet<String> ALTS;
    private final String NAME;

    protected Species(ISpeciesBuilder<T, ?> builder) {
        GENERATIONS = builder.getGenerations();
        TYPES = builder.getTypes();
        TIERS = builder.getTiers();
        STATS = builder.getStats();
        EVOLUTIONS = builder.getEvolutions();
        HEIGHT = builder.getHeight();
        WEIGHT = builder.getWeight();
        GENDERS = builder.getGenders();
        ALTS = builder.getAlts();
        NAME = builder.getName();
    }

    @Override
    public ImmutableSet<String> getGenerations() {
        return GENERATIONS;
    }

    @Override
    public ImmutableSet<IType> getTypes() {
        return TYPES;
    }

    @Override
    public ImmutableSet<IFormat> getTier() {
        return TIERS;
    }

    @Override
    public ImmutableMap<IStat, Integer> getStats() {
        return STATS;
    }

    @Override
    public ImmutableSet<T> getEvolutions() {
        return EVOLUTIONS;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    @Override
    public ImmutableSet<Gender> getGenders() {
        return GENDERS;
    }

    @Override
    public ImmutableSet<String> getAlts() {
        return ALTS;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
