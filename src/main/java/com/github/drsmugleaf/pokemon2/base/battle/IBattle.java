package com.github.drsmugleaf.pokemon2.base.battle;

import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.trainer.ITrainer;

import java.util.Set;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface IBattle<T extends ISpecies<T>> {

    IGeneration getGeneration();
    Set<ITrainer<T>> getTrainers();

}
