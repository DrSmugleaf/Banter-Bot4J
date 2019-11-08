package com.github.drsmugleaf.pokemon2.generations.iii.species;

import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.IAbility;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public interface ISpeciesIII<T extends ISpeciesIII<T>> extends ISpecies<T> {

    ImmutableSet<IAbility> getAbilities();

}
