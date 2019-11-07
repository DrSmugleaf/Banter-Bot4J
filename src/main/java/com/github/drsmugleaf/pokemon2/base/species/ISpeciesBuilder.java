package com.github.drsmugleaf.pokemon2.base.species;

import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.pokemon.Gender;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.type.IType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public interface ISpeciesBuilder<T extends ISpecies> {

    String getName();
    ISpeciesBuilder<T> setName(String name);
    ImmutableSet<String> getGenerations();
    ISpeciesBuilder<T> setGenerations(Collection<String> generations);
    ImmutableSet<IType> getTypes();
    ISpeciesBuilder<T> setTypes(Collection<? extends IType> types);
    ImmutableSet<Tier> getTiers();
    ISpeciesBuilder<T> setTiers(Collection<Tier> tiers);
    ImmutableMap<PermanentStat, Integer> getStats();
    ISpeciesBuilder<T> addStat(PermanentStat stat, Integer amount);
    ISpeciesBuilder<T> setStats(Map<PermanentStat, Integer> stats);
    ImmutableSet<T> getEvolutions();
    ISpeciesBuilder<T> setEvolutions(Collection<T> evolutions);
    Double getWeight();
    ISpeciesBuilder<T> setWeight(double weight);
    Double getHeight();
    ISpeciesBuilder<T> setHeight(double height);
    ImmutableSet<Gender> getGenders();
    ISpeciesBuilder<T> setGenders(Collection<Gender> genders);
    ImmutableSet<String> getAlts();
    ISpeciesBuilder<T> setAlts(Collection<String> alts);

}
