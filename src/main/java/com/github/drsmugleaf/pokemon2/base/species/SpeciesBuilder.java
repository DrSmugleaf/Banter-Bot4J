package com.github.drsmugleaf.pokemon2.base.species;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.pokemon.Gender;
import com.github.drsmugleaf.pokemon2.base.species.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.species.type.IType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

import java.util.*;

/**
 * Created by DrSmugleaf on 03/07/2019
 */
public class SpeciesBuilder<T extends ISpecies, B extends SpeciesBuilder<T, B> & ISpeciesBuilder<T, B>> implements ISpeciesBuilder<T, B> {

    @Nullable
    private String name;
    @Nullable
    private Set<String> generations;
    @Nullable
    private Set<IType> types;
    @Nullable
    private Set<Tier> tiers;
    private Map<IStat, Integer> stats = new HashMap<>();
    private Set<T> evolutions = new HashSet<>(); // TODO: 06-Jul-19 Add valid evolutions
    @Nullable
    private Double weight;
    @Nullable
    private Double height;
    private Set<Gender> genders = new HashSet<>(); // TODO: 06-Jul-19 Add valid genders
    private Set<String> alts = new HashSet<>();

    public SpeciesBuilder() {}

    public SpeciesBuilder(ISpeciesBuilder<T, ?> builder) {
        name = builder.getName();
        generations = builder.getGenerations();
        types = builder.getTypes();
        tiers = builder.getTiers();
        stats = builder.getStats();
        evolutions = builder.getEvolutions();
        weight = builder.getWeight();
        height = builder.getHeight();
        genders = builder.getGenders();
        alts = builder.getAlts();
    }

    @Contract(" -> new")
    @SuppressWarnings("unchecked")
    public static <T extends ISpecies, B extends SpeciesBuilder<T, B>> SpeciesBuilder<T, B> create() {
        return (B) new SpeciesBuilder<>();
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
    public ImmutableSet<String> getGenerations() {
        return ImmutableSet.copyOf(generations);
    }

    @Override
    public B setGenerations(Collection<String> generations) {
        this.generations = new HashSet<>(generations);
        return getThis();
    }

    @Override
    public ImmutableSet<IType> getTypes() {
        return ImmutableSet.copyOf(types);
    }

    @Override
    public B setTypes(Collection<? extends IType> types) {
        this.types = new HashSet<>(types);
        return getThis();
    }

    @Override
    public ImmutableSet<Tier> getTiers() {
        return ImmutableSet.copyOf(tiers);
    }

    @Override
    public B setTiers(Collection<Tier> tiers) {
        this.tiers = new HashSet<>(tiers);
        return getThis();
    }

    @Override
    public ImmutableMap<IStat, Integer> getStats() {
        return ImmutableMap.copyOf(stats);
    }

    @Override
    public B addStat(IStat stat, Integer amount) {
        this.stats.put(stat, amount);
        return getThis();
    }

    @Override
    public B setStats(Map<IStat, Integer> stats) {
        this.stats = stats;
        return getThis();
    }

    @Override
    public ImmutableSet<T> getEvolutions() {
        return ImmutableSet.copyOf(evolutions);
    }

    @Override
    public B setEvolutions(Collection<T> evolutions) {
        this.evolutions = new HashSet<>(evolutions);
        return getThis();
    }

    @Nullable
    @Override
    public Double getWeight() {
        return weight;
    }

    @Override
    public B setWeight(double weight) {
        this.weight = weight;
        return getThis();
    }

    @Nullable
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
    public ImmutableSet<Gender> getGenders() {
        return ImmutableSet.copyOf(genders);
    }

    @Override
    public B setGenders(Collection<Gender> genders) {
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
