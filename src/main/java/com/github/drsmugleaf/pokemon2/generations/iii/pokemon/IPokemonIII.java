package com.github.drsmugleaf.pokemon2.generations.iii.pokemon;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.ability.IAbility;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.species.ISpeciesIII;
import com.github.drsmugleaf.pokemon2.generations.iii.pokemon.stat.nature.INature;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public interface IPokemonIII<T extends ISpeciesIII<T>> extends IPokemon<T>, ISpeciesIII<T> {

    @Override
    T getSpecies();
    INature getNature();
    @Override
    default ImmutableSet<IAbility> getAbilities() {
        return getSpecies().getAbilities();
    }

}
