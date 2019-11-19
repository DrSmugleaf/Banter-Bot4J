package com.github.drsmugleaf.pokemon2.base.battle.turn.snapshot;

import com.github.drsmugleaf.pokemon2.base.battle.IBattle;
import com.github.drsmugleaf.pokemon2.base.pokemon.IBattlePokemon;

/**
 * Created by DrSmugleaf on 19/11/2019
 */
public interface ISnapshot<T extends IBattlePokemon> {

    IBattle<T> getBattle();

}
