package com.github.drsmugleaf.pokemon.generations.iii.pokemon;

import com.github.drsmugleaf.pokemon.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon.base.pokemon.species.SpeciesRegistry;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.ability.IAbility;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.species.ISpeciesIII;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.stat.nature.INature;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 15/11/2019
 */
public interface IPokemonIII<T extends IPokemonIII<T>> extends IPokemon<T>, ISpeciesIII {

    INature getNature();
    IAbility getAbility();
    @Override
    ISpeciesIII getSpecies();

    @Override
    default ImmutableSet<IAbility> getAbilities() {
        return getSpecies().getAbilities();
    }

    @Override
    default SpeciesRegistry<? extends ISpeciesIII> getEvolutions() {
        return getSpecies().getEvolutions();
    }

}
