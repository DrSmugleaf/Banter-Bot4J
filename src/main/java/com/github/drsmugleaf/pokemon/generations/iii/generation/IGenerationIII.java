package com.github.drsmugleaf.pokemon.generations.iii.generation;

import com.github.drsmugleaf.pokemon.base.pokemon.species.SpeciesRegistry;
import com.github.drsmugleaf.pokemon.generations.ii.generation.IGenerationII;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.ability.AbilityRegistry;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.stat.nature.NatureRegistry;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.species.ISpeciesIII;

/**
 * Created by DrSmugleaf on 11/11/2019
 */
public interface IGenerationIII extends IGenerationII {

    @Override
    SpeciesRegistry<? extends ISpeciesIII> getPokedex();
    NatureRegistry getNatures();
    AbilityRegistry getAbilities();

}
