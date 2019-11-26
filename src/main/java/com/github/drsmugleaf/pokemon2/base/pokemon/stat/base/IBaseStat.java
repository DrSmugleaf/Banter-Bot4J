package com.github.drsmugleaf.pokemon2.base.pokemon.stat.base;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;

/**
 * Created by DrSmugleaf on 16/11/2019
 */
public interface IBaseStat<T extends IPokemon<T>> extends IStatType, Nameable {

    int calculate(T pokemon);

}
