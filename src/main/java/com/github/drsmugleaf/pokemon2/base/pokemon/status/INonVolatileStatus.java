package com.github.drsmugleaf.pokemon2.base.pokemon.status;

import com.github.drsmugleaf.pokemon2.base.pokemon.IBattlePokemon;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public interface INonVolatileStatus<T extends IBattlePokemon> extends IStatus<T> {

    @Override
    default boolean isVolatileStatus() {
        return false;
    }
    @Override
    default boolean isBattleStatus() {
        return false;
    }

}
