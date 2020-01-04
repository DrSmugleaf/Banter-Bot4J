package com.github.drsmugleaf.pokemon.base.battle.turn.snapshot;

import com.github.drsmugleaf.pokemon.base.battle.IBattle;
import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;

/**
 * Created by DrSmugleaf on 19/11/2019
 */
public interface ISnapshot<T extends IBattlePokemon<T>> {

    IBattle<T> getBattle();

}
