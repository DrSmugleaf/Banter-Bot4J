package com.github.drsmugleaf.pokemon.base.pokemon.species;

import com.github.drsmugleaf.pokemon.base.format.FormatRegistry;
import com.github.drsmugleaf.pokemon.base.generation.GenerationRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon.base.pokemon.type.TypeRegistry;
import com.github.drsmugleaf.pokemon.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public abstract class BaseSpecies<T extends BaseSpecies<T>> implements ISpecies {

    private final GenerationRegistry GENERATIONS;
    private final TypeRegistry TYPES;
    private final FormatRegistry TIERS;
    private final ImmutableMap<IStatType, Integer> STATS;
    private final Pokedex<T> EVOLUTIONS;
    private final double HEIGHT;
    private final double WEIGHT;
    private final ImmutableSet<IGender> GENDERS;
    private final ImmutableSet<String> ALTS;
    private final String NAME;

    protected BaseSpecies(ISpeciesBuilder<T, ?> builder) {
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
    
    protected BaseSpecies(T species) {
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
    public GenerationRegistry getGenerations() {
        return GENERATIONS;
    }

    @Override
    public TypeRegistry getSpeciesTypes() {
        return TYPES;
    }

    @Override
    public FormatRegistry getTiers() {
        return TIERS;
    }

    @Override
    public ImmutableMap<IStatType, Integer> getSpeciesStats() {
        return STATS;
    }

    @Override
    public Pokedex<T> getEvolutions() {
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
