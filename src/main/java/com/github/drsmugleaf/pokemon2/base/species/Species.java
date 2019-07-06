package com.github.drsmugleaf.pokemon2.base.species;

import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.pokemon.Gender;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.ability.IAbility;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.type.IType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public abstract class Species implements ISpecies {

    private final ImmutableSet<IGeneration> GENERATIONS;
    private final ImmutableSet<IAbility> ABILITIES;
    private final ImmutableSet<IType> TYPES;
    private final ImmutableSet<Tier> TIERS;
    private final ImmutableMap<PermanentStat, Integer> STATS;
    private final ImmutableSet<ISpecies> EVOLUTIONS;
    private final double HEIGHT;
    private final double WEIGHT;
    private final String SUFFIX;
    private final ImmutableSet<Gender> GENDERS;
    private final String NAME;

    protected Species(SpeciesBuilder builder) {
        GENERATIONS = builder.getGenerations();
        ABILITIES = builder.getAbilities();
        TYPES = builder.getTypes();
        TIERS = builder.getTiers();
        STATS = builder.getStats();
        EVOLUTIONS = builder.getEvolutions();
        HEIGHT = builder.getHeight();
        WEIGHT = builder.getWeight();
        SUFFIX = builder.getSuffix();
        GENDERS = builder.getGenders();
        NAME = builder.getName();
    }

    @Override
    public ImmutableSet<IGeneration> getGenerations() {
        return GENERATIONS;
    }

    @Override
    public ImmutableSet<IAbility> getAbilities() {
        return ABILITIES;
    }

    @Override
    public ImmutableSet<IType> getTypes() {
        return TYPES;
    }

    @Override
    public ImmutableSet<Tier> getTiers() {
        return TIERS;
    }

    @Override
    public ImmutableMap<PermanentStat, Integer> getStats() {
        return STATS;
    }

    @Override
    public ImmutableSet<ISpecies> getEvolutions() {
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
    public String getSuffix() {
        return SUFFIX;
    }

    @Override
    public ImmutableSet<Gender> getGenders() {
        return GENDERS;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
