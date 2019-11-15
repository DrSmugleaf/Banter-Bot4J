package com.github.drsmugleaf.pokemon2.base.pokemon.species;

import com.github.drsmugleaf.pokemon2.base.builder.IBuilder;
import com.github.drsmugleaf.pokemon2.base.format.IFormat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IBaseStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public interface ISpeciesBuilder<T extends ISpecies, B extends ISpeciesBuilder<T, B>> extends IBuilder<B> {

    String getName();
    B setName(String name);
    ImmutableSet<String> getGenerations();
    B setGenerations(Collection<String> generations);
    ImmutableSet<IType> getTypes();
    B setTypes(Collection<? extends IType> types);
    ImmutableSet<IFormat> getTiers();
    B setTiers(Collection<IFormat> tiers);
    ImmutableMap<IBaseStat, Integer> getStats();
    B addStat(IBaseStat stat, Integer amount);
    B setStats(Map<IBaseStat, Integer> stats);
    ImmutableSet<T> getEvolutions();
    B setEvolutions(Collection<T> evolutions);
    Double getWeight();
    B setWeight(double weight);
    Double getHeight();
    B setHeight(double height);
    ImmutableSet<IGender> getGenders();
    B setGenders(Collection<IGender> genders);
    ImmutableSet<String> getAlts();
    B setAlts(Collection<String> alts);

}
