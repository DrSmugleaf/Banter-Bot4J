package com.github.drsmugleaf.pokemon2.base.pokemon.species;

import com.github.drsmugleaf.pokemon2.base.format.IFormat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public abstract class Species<T extends ISpecies> implements ISpecies {

    private final ImmutableSet<String> GENERATIONS;
    private final ImmutableSet<IType> TYPES;
    private final ImmutableSet<IFormat> TIERS;
    private final ImmutableMap<IStatType, Integer> STATS;
    private final ImmutableSet<? extends ISpecies> EVOLUTIONS;
    private final double HEIGHT;
    private final double WEIGHT;
    private final ImmutableSet<IGender> GENDERS;
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
    
    protected Species(T species) {
        GENERATIONS = species.getGenerations();
        TYPES = species.getSpeciesTypes();
        TIERS = species.getTiers();
        STATS = species.getSpeciesStats();
        EVOLUTIONS = species.getEvolutions();
        HEIGHT = species.getHeight();
        WEIGHT = species.getWeight();
        GENDERS = species.getGenders();
        ALTS = species.getAlts();
        NAME = species.getName();
    }

    @Override
    public ImmutableSet<String> getGenerations() {
        return GENERATIONS;
    }

    @Override
    public ImmutableSet<IType> getSpeciesTypes() {
        return TYPES;
    }

    @Override
    public ImmutableSet<IFormat> getTiers() {
        return TIERS;
    }

    @Override
    public ImmutableMap<IStatType, Integer> getSpeciesStats() {
        return STATS;
    }

    @Override
    public ImmutableSet<? extends ISpecies> getEvolutions() {
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
    public ImmutableSet<IGender> getGenders() {
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
