package com.github.drsmugleaf.pokemon2.base.pokemon.status.volatile_.battle;

import com.github.drsmugleaf.pokemon2.base.battle.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.volatile_.IVolatileStatus;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public interface IVolatileBattleStatus<T extends IBattlePokemon> extends IVolatileStatus<T> {

    @Override
    default boolean isBattleStatus() {
        return true;
    }

}
