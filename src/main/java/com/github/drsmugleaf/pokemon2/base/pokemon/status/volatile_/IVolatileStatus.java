package com.github.drsmugleaf.pokemon2.base.pokemon.status.volatile_;

import com.github.drsmugleaf.pokemon2.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.IStatus;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public interface IVolatileStatus<T extends IBattlePokemon<T>> extends IStatus<T> {

    @Override
    default boolean isVolatileStatus() {
        return true;
    }
    @Override
    default boolean isBattleStatus() {
        return false;
    }

}
