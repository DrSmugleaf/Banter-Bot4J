package com.github.drsmugleaf.pokemon2.base.battle.side;

import com.github.drsmugleaf.pokemon2.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.trainer.IBattleTrainer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface ISide<T extends IBattlePokemon<T>> {

    ImmutableSet<IBattleTrainer<T>> getTrainers();
    ImmutableList<IPokemon> getActivePokemon();
    int getSize();

}
