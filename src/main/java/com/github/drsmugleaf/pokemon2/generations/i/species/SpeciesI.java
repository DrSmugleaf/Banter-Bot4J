package com.github.drsmugleaf.pokemon2.generations.i.species;

import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.pokemon.Gender;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.ability.IAbility;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon2.base.type.IType;
import com.github.drsmugleaf.pokemon2.generations.i.GenerationI;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

import java.util.Map;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class SpeciesI implements ISpecies {

    private static final ImmutableMap<String, ISpecies> SPECIES = ImmutableMap.copyOf(getAll());

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

    protected SpeciesI(SpeciesBuilder builder) {
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

    protected static Map<String, ISpecies> getAll() {
        return GenerationI.get().getSmogon().getSpecies(SpeciesI::new);
    }

    @Contract(pure = true)
    public static ImmutableMap<String, ISpecies> all() {
        return SPECIES;
    }

    @Override
    public ImmutableMap<String, ISpecies> getSpecies() {
        return all();
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
