package com.github.drsmugleaf.pokemon2.base.trainer;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.trainer.party.IParty;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface ITrainer<T extends ISpecies<T>> extends Nameable {

    IParty<T> getParty();

}
