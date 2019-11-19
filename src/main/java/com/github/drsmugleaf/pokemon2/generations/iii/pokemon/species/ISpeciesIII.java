package com.github.drsmugleaf.pokemon2.generations.iii.pokemon.species;

import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.ability.IAbility;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public interface ISpeciesIII<T extends ISpeciesIII<T>> extends ISpecies {

    ImmutableSet<IAbility> getAbilities();

}
