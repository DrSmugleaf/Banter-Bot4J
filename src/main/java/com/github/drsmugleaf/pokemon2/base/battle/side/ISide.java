package com.github.drsmugleaf.pokemon2.base.battle.side;

import com.github.drsmugleaf.pokemon2.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.trainer.IBattleTrainer;

import java.util.List;
import java.util.Set;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface ISide<T extends IBattlePokemon<T>> {

    Set<IBattleTrainer<T>> getTrainers();
    List<IPokemon> getActivePokemon();
    int getSize();

}
