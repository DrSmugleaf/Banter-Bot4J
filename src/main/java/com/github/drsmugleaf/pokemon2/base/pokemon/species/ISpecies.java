package com.github.drsmugleaf.pokemon2.base.pokemon.species;

import com.github.drsmugleaf.pokemon2.base.format.IFormat;
import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface ISpecies extends Nameable {

    ImmutableSet<String> getGenerations();
    ImmutableSet<IType> getSpeciesTypes();
    ImmutableSet<IFormat> getTiers();
    ImmutableMap<IStatType, Integer> getSpeciesStats();
    ImmutableSet<? extends ISpecies> getEvolutions();
    double getHeight();
    double getWeight();
    ImmutableSet<IGender> getGenders();
    ImmutableSet<String> getAlts();

}
