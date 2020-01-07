package com.github.drsmugleaf.pokemon.base.pokemon.species;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.base.format.FormatRegistry;
import com.github.drsmugleaf.pokemon.base.format.IFormat;
import com.github.drsmugleaf.pokemon.base.generation.GenerationRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon.base.pokemon.type.TypeRegistry;
import com.github.drsmugleaf.pokemon.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.*;

/**
 * Created by DrSmugleaf on 03/07/2019
 */
public abstract class SpeciesBuilder<T extends ISpecies, B extends SpeciesBuilder<T, B> & ISpeciesBuilder<T, B>> implements ISpeciesBuilder<T, B> {

    @Nullable
    private String name;
    private GenerationRegistry generations;
    private TypeRegistry types;
    private FormatRegistry tiers;
    private Map<IStatType, Integer> stats = new HashMap<>();
    private Double weight;
    private Double height;
    private Set<IGender> genders = new HashSet<>(); // TODO: 06-Jul-19 Add valid genders
    private Set<String> alts = new HashSet<>();

    public SpeciesBuilder() {}

    public SpeciesBuilder(ISpeciesBuilder<T, ?> builder) {
        name = builder.getName();
        generations = builder.getGenerations();
        types = builder.getTypes();
        tiers = builder.getTiers();
        stats = builder.getStats();
        weight = builder.getWeight();
        height = builder.getHeight();
        genders = builder.getGenders();
        alts = builder.getAlts();
    }

    @Nullable
    @Override
    public String getName() {
        return name;
    }

    @Override
    public B setName(String name) {
        this.name = name;
        return getThis();
    }

    @Override
    public GenerationRegistry getGenerations() {
        return generations;
    }

    @Override
    public B setGenerations(Collection<String> generationNames) {
        this.generations = new GenerationRegistry(generationNames);
        return getThis();
    }

    @Override
    public TypeRegistry getTypes() {
        return types;
    }

    @Override
    public B setTypes(Collection<IType> types) {
        this.types = new TypeRegistry(types);
        return getThis();
    }

    @Override
    public FormatRegistry getTiers() {
        return tiers;
    }

    @Override
    public B setTiers(Collection<IFormat> tiers) {
        this.tiers = new FormatRegistry(tiers);
        return getThis();
    }

    @Override
    public ImmutableMap<IStatType, Integer> getStats() {
        return ImmutableMap.copyOf(stats);
    }

    @Override
    public B addStat(IStatType stat, Integer amount) {
        this.stats.put(stat, amount);
        return getThis();
    }

    @Override
    public B setStats(Map<IStatType, Integer> stats) {
        this.stats = stats;
        return getThis();
    }

    @Override
    public Double getWeight() {
        return weight;
    }

    @Override
    public B setWeight(double weight) {
        this.weight = weight;
        return getThis();
    }

    @Override
    public Double getHeight() {
        return height;
    }

    @Override
    public B setHeight(double height) {
        this.height = height;
        return getThis();
    }

    @Override
    public ImmutableSet<IGender> getGenders() {
        return ImmutableSet.copyOf(genders);
    }

    @Override
    public B setGenders(Collection<IGender> genders) {
        this.genders = new HashSet<>(genders);
        return getThis();
    }

    @Override
    public ImmutableSet<String> getAlts() {
        return ImmutableSet.copyOf(alts);
    }

    @Override
    public B setAlts(Collection<String> alts) {
        this.alts = new HashSet<>(alts);
        return getThis();
    }

}
