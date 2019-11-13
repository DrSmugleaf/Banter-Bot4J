package com.github.drsmugleaf.pokemon2.base.species;

import com.github.drsmugleaf.pokemon.pokemon.Gender;
import com.github.drsmugleaf.pokemon2.base.format.IFormat;
import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.species.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.species.type.IType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface ISpecies<T extends ISpecies<T>> extends Nameable {

    ImmutableSet<String> getGenerations();
    ImmutableSet<IType> getTypes();
    ImmutableSet<IFormat> getTier();
    ImmutableMap<IStat, Integer> getStats();
    ImmutableSet<T> getEvolutions();
    double getHeight();
    double getWeight();
    ImmutableSet<Gender> getGenders();
    ImmutableSet<String> getAlts();

}
