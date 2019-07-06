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
import javafx.util.Builder;

import java.util.*;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 03/07/2019
 */
public class SpeciesBuilder<T extends ISpecies> implements Builder<T> {

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
    private Set<T> evolutions = new HashSet<>(); // TODO: 06-Jul-19 Add valid evolutions
    @Nullable
    private Double weight;
    @Nullable
    private Double height;
    private String suffix = "";
    private Set<Gender> genders = new HashSet<>(); // TODO: 06-Jul-19 Add valid genders
    private final Function<SpeciesBuilder<T>, T> CONSTRUCTOR;

    public SpeciesBuilder(Function<SpeciesBuilder<T>, T> constructor) {
        CONSTRUCTOR = constructor;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public SpeciesBuilder<T> setName(String name) {
        this.name = name;
        return this;
    }

    public ImmutableSet<IGeneration> getGenerations() {
        return ImmutableSet.copyOf(generations);
    }

    public SpeciesBuilder<T> setGenerations(Collection<IGeneration> generations) {
        this.generations = new HashSet<>(generations);
        return this;
    }

    public ImmutableSet<IAbility> getAbilities() {
        return ImmutableSet.copyOf(abilities);
    }

    public SpeciesBuilder<T> setAbilities(Collection<? extends IAbility> abilities) {
        this.abilities = new HashSet<>(abilities);
        return this;
    }

    public ImmutableSet<IType> getTypes() {
        return ImmutableSet.copyOf(types);
    }

    public SpeciesBuilder<T> setTypes(Collection<? extends IType> types) {
        this.types = new HashSet<>(types);
        return this;
    }

    public ImmutableSet<Tier> getTiers() {
        return ImmutableSet.copyOf(tiers);
    }

    public SpeciesBuilder<T> setTiers(Collection<Tier> tiers) {
        this.tiers = new HashSet<>(tiers);
        return this;
    }

    public ImmutableMap<PermanentStat, Integer> getStats() {
        return ImmutableMap.copyOf(stats);
    }

    public SpeciesBuilder<T> addStat(PermanentStat stat, Integer amount) {
        this.stats.put(stat, amount);
        return this;
    }

    public SpeciesBuilder<T> setStats(Map<PermanentStat, Integer> stats) {
        this.stats = stats;
        return this;
    }

    public ImmutableSet<T> getEvolutions() {
        return ImmutableSet.copyOf(evolutions);
    }

    public SpeciesBuilder<T> setEvolutions(Collection<T> evolutions) {
        this.evolutions = new HashSet<>(evolutions);
        return this;
    }

    @Nullable
    public Double getWeight() {
        return weight;
    }

    public SpeciesBuilder<T> setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    @Nullable
    public Double getHeight() {
        return height;
    }

    public SpeciesBuilder<T> setHeight(double height) {
        this.height = height;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public SpeciesBuilder<T> setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public ImmutableSet<Gender> getGenders() {
        return ImmutableSet.copyOf(genders);
    }

    public SpeciesBuilder<T> setGenders(Collection<Gender> genders) {
        this.genders = new HashSet<>(genders);
        return this;
    }

    @Override
    public T build() {
        return CONSTRUCTOR.apply(this);
    }

}
