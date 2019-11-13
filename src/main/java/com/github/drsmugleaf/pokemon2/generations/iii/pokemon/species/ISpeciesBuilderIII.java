package com.github.drsmugleaf.pokemon2.generations.iii.pokemon.species;

import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpeciesBuilder;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.ability.IAbility;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public interface ISpeciesBuilderIII<T extends ISpecies, B extends ISpeciesBuilderIII<T, B>> extends ISpeciesBuilder<T, B> {

    ImmutableSet<IAbility> getAbilities();
    B setAbilities(Collection<IAbility> abilities);

}
