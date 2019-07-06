package com.github.drsmugleaf.pokemon2.base.species;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.pokemon.Gender;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.ability.IAbility;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.type.IType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.*;

/**
 * Created by DrSmugleaf on 03/07/2019
 */
public class SpeciesBuilder {

    @Nullable
    private String name;
    @Nullable
    private Set<IGeneration> generations;
    @Nullable
    private Set<IAbility> abilities;
    @Nullable
    private Set<IType> types;
    @Nullable
    private Set<Tier> tiers;
    private Map<PermanentStat, Integer> stats = new HashMap<>();
    private Set<ISpecies> evolutions = new HashSet<>(); // TODO: 06-Jul-19 Add valid evolutions
    @Nullable
    private Double weight;
    @Nullable
    private Double height;
    private String suffix = "";
    private Set<Gender> genders = new HashSet<>(); // TODO: 06-Jul-19 Add valid genders

    public SpeciesBuilder() {}

    @Nullable
    public String getName() {
        return name;
    }

    public SpeciesBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ImmutableSet<IGeneration> getGenerations() {
        return ImmutableSet.copyOf(generations);
    }

    public SpeciesBuilder setGenerations(Collection<IGeneration> generations) {
        this.generations = new HashSet<>(generations);
        return this;
    }

    public ImmutableSet<IAbility> getAbilities() {
        return ImmutableSet.copyOf(abilities);
    }

    public SpeciesBuilder setAbilities(Collection<? extends IAbility> abilities) {
        this.abilities = new HashSet<>(abilities);
        return this;
    }

    public ImmutableSet<IType> getTypes() {
        return ImmutableSet.copyOf(types);
    }

    public SpeciesBuilder setTypes(Collection<? extends IType> types) {
        this.types = new HashSet<>(types);
        return this;
    }

    public ImmutableSet<Tier> getTiers() {
        return ImmutableSet.copyOf(tiers);
    }

    public SpeciesBuilder setTiers(Collection<Tier> tiers) {
        this.tiers = new HashSet<>(tiers);
        return this;
    }

    public ImmutableMap<PermanentStat, Integer> getStats() {
        return ImmutableMap.copyOf(stats);
    }

    public SpeciesBuilder addStat(PermanentStat stat, Integer amount) {
        this.stats.put(stat, amount);
        return this;
    }

    public SpeciesBuilder setStats(Map<PermanentStat, Integer> stats) {
        this.stats = stats;
        return this;
    }

    public ImmutableSet<ISpecies> getEvolutions() {
        return ImmutableSet.copyOf(evolutions);
    }

    public SpeciesBuilder setEvolutions(Collection<ISpecies> evolutions) {
        this.evolutions = new HashSet<>(evolutions);
        return this;
    }

    @Nullable
    public Double getWeight() {
        return weight;
    }

    public SpeciesBuilder setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    @Nullable
    public Double getHeight() {
        return height;
    }

    public SpeciesBuilder setHeight(double height) {
        this.height = height;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public SpeciesBuilder setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public ImmutableSet<Gender> getGenders() {
        return ImmutableSet.copyOf(genders);
    }

    public SpeciesBuilder setGenders(Collection<Gender> genders) {
        this.genders = new HashSet<>(genders);
        return this;
    }

}
