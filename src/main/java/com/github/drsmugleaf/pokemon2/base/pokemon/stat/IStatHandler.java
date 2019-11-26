package com.github.drsmugleaf.pokemon2.base.pokemon.stat;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.google.common.collect.ImmutableMap;

/**
 * Created by DrSmugleaf on 19/11/2019
 */
public interface IStatHandler<T extends IPokemon<T>> {

    ImmutableMap<IStatType, IStat<T>> get();
    IStat<T> get(IStatType type);

}
