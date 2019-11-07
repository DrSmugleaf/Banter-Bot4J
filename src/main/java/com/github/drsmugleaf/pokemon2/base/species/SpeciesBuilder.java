package com.github.drsmugleaf.pokemon2.base.species;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.pokemon.Gender;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.type.IType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.*;

/**
 * Created by DrSmugleaf on 03/07/2019
 */
public class SpeciesBuilder<T extends ISpecies> implements ISpeciesBuilder<T> {

    @Nullable
    private String name;
    @Nullable
    private Set<String> generations;
    @Nullable
    private Set<IType> types;
    @Nullable
    private Set<Tier> tiers;
    private Map<PermanentStat, Integer> stats = new HashMap<>();
    private Set<T> evolutions = new HashSet<>(); // TODO: 06-Jul-19 Add valid evolutions
    @Nullable
    private Double weight;
    @Nullable
    private Double height;
    private String suffix = "";
    private Set<Gender> genders = new HashSet<>(); // TODO: 06-Jul-19 Add valid genders
    private Set<String> alts = new HashSet<>();

    public SpeciesBuilder() {}

    public SpeciesBuilder(SpeciesBuilder<T> builder) {
        name = builder.name;
        generations = builder.generations;
        types = builder.types;
        tiers = builder.tiers;
        stats = builder.stats;
        evolutions = builder.evolutions;
        weight = builder.weight;
        height = builder.height;
        suffix = builder.suffix;
        genders = builder.genders;
    }

    @Nullable
    @Override
    public String getName() {
        return name;
    }

    @Override
    public SpeciesBuilder<T> setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ImmutableSet<String> getGenerations() {
        return ImmutableSet.copyOf(generations);
    }

    @Override
    public SpeciesBuilder<T> setGenerations(Collection<String> generations) {
        this.generations = new HashSet<>(generations);
        return this;
    }

    @Override
    public ImmutableSet<IType> getTypes() {
        return ImmutableSet.copyOf(types);
    }

    @Override
    public SpeciesBuilder<T> setTypes(Collection<? extends IType> types) {
        this.types = new HashSet<>(types);
        return this;
    }

    @Override
    public ImmutableSet<Tier> getTiers() {
        return ImmutableSet.copyOf(tiers);
    }

    @Override
    public SpeciesBuilder<T> setTiers(Collection<Tier> tiers) {
        this.tiers = new HashSet<>(tiers);
        return this;
    }

    @Override
    public ImmutableMap<PermanentStat, Integer> getStats() {
        return ImmutableMap.copyOf(stats);
    }

    @Override
    public SpeciesBuilder<T> addStat(PermanentStat stat, Integer amount) {
        this.stats.put(stat, amount);
        return this;
    }

    @Override
    public SpeciesBuilder<T> setStats(Map<PermanentStat, Integer> stats) {
        this.stats = stats;
        return this;
    }

    @Override
    public ImmutableSet<T> getEvolutions() {
        return ImmutableSet.copyOf(evolutions);
    }

    @Override
    public SpeciesBuilder<T> setEvolutions(Collection<T> evolutions) {
        this.evolutions = new HashSet<>(evolutions);
        return this;
    }

    @Nullable
    @Override
    public Double getWeight() {
        return weight;
    }

    @Override
    public SpeciesBuilder<T> setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    @Nullable
    @Override
    public Double getHeight() {
        return height;
    }

    @Override
    public SpeciesBuilder<T> setHeight(double height) {
        this.height = height;
        return this;
    }

    @Override
    public ImmutableSet<Gender> getGenders() {
        return ImmutableSet.copyOf(genders);
    }

    @Override
    public SpeciesBuilder<T> setGenders(Collection<Gender> genders) {
        this.genders = new HashSet<>(genders);
        return this;
    }

    @Override
    public ImmutableSet<String> getAlts() {
        return ImmutableSet.copyOf(alts);
    }

    @Override
    public SpeciesBuilder<T> setAlts(Collection<String> alts) {
        this.alts = new HashSet<>(alts);
        return this;
    }

}
