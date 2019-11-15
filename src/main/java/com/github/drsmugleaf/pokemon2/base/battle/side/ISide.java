package com.github.drsmugleaf.pokemon2.base.battle.side;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.trainer.ITrainer;

import java.util.List;
import java.util.Set;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface ISide<T extends ISpecies<T>> {

    Set<ITrainer<T>> getTrainers();
    List<IPokemon<T>> getActivePokemon();
    int getSize();


}
