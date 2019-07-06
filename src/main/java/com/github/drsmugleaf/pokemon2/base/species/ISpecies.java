package com.github.drsmugleaf.pokemon2.base.species;

import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.pokemon.Gender;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.Nameable;
import com.github.drsmugleaf.pokemon2.base.ability.IAbility;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.type.IType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface ISpecies extends Nameable {

    ImmutableMap<String, ? extends ISpecies> getSpecies();
    ImmutableSet<IGeneration> getGenerations();
    ImmutableSet<IAbility> getAbilities();
    ImmutableSet<IType> getTypes();
    ImmutableSet<Tier> getTiers();
    ImmutableMap<PermanentStat, Integer> getStats();
    ImmutableSet<ISpecies> getEvolutions();
    double getHeight();
    double getWeight();
    String getSuffix();
    ImmutableSet<Gender> getGenders();

}
