package com.github.drsmugleaf.pokemon.base.trainer;

import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon.base.trainer.party.IParty;

/**
 * Created by DrSmugleaf on 19/11/2019
 */
public interface IBattleTrainer<T extends IBattlePokemon<T>> extends ITrainer {

    IParty<T> getParty();

}
