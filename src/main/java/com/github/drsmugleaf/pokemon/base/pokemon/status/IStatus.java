package com.github.drsmugleaf.pokemon.base.pokemon.status;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon.base.nameable.Nameable;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IStatus<T extends IBattlePokemon<T>> extends Nameable {

    boolean isVolatileStatus();
    boolean isBattleStatus();
    @Nullable
    Integer getDuration();
    void apply(T pokemon);
    void remove(T pokemon);

}
