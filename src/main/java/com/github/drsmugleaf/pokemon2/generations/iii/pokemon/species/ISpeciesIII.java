package com.github.drsmugleaf.pokemon2.generations.iii.pokemon.species;

import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.ability.IAbility;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public interface ISpeciesIII extends ISpecies {

    ImmutableSet<IAbility> getAbilities();
    @Override
    ImmutableSet<? extends ISpeciesIII> getEvolutions();

}
