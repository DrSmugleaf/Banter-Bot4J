package com.github.drsmugleaf.pokemon2.generations.iii.generation;

import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.AbilityRegistry;
import com.github.drsmugleaf.pokemon2.generations.iii.nature.NatureRegistry;
import com.github.drsmugleaf.pokemon2.generations.iii.species.ISpeciesIII;

/**
 * Created by DrSmugleaf on 11/11/2019
 */
public interface IGenerationIII extends IGeneration {

    @Override
    Pokedex<? extends ISpeciesIII<?>> getPokedex();
    NatureRegistry getNatures();
    AbilityRegistry getAbilities();

}
