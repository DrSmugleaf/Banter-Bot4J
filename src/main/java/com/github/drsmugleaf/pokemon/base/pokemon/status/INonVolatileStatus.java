package com.github.drsmugleaf.pokemon.base.pokemon.status;

import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public interface INonVolatileStatus<T extends IBattlePokemon<T>> extends IStatus<T> {

    @Override
    default boolean isVolatileStatus() {
        return false;
    }
    @Override
    default boolean isBattleStatus() {
        return false;
    }

}
