package com.github.drsmugleaf.pokemon.base.pokemon.species;

import com.github.drsmugleaf.pokemon.base.builder.IBuilder;
import com.github.drsmugleaf.pokemon.base.format.FormatRegistry;
import com.github.drsmugleaf.pokemon.base.format.IFormat;
import com.github.drsmugleaf.pokemon.base.generation.GenerationRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon.base.pokemon.type.TypeRegistry;
import com.github.drsmugleaf.pokemon.generations.ii.pokemon.gender.IGender;
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
    GenerationRegistry getGenerations();
    B setGenerations(Collection<String> generations);
    TypeRegistry getTypes();
    B setTypes(Collection<IType> types);
    FormatRegistry getTiers();
    B setTiers(Collection<IFormat> tiers);
    ImmutableMap<IStatType, Integer> getStats();
    B addStat(IStatType stat, Integer amount);
    B setStats(Map<IStatType, Integer> stats);
    SpeciesRegistry<T> getEvolutions();
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
