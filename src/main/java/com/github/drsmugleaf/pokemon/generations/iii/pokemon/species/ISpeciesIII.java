package com.github.drsmugleaf.pokemon.generations.iii.pokemon.species;

import com.github.drsmugleaf.pokemon.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.ability.IAbility;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public interface ISpeciesIII extends ISpecies {

    ImmutableSet<IAbility> getAbilities();
    @Override
    Pokedex<? extends ISpeciesIII> getEvolutions();

}
