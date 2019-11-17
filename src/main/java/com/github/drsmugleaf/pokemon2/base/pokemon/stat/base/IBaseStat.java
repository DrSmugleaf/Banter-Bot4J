package com.github.drsmugleaf.pokemon2.base.pokemon.stat.base;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;

/**
 * Created by DrSmugleaf on 16/11/2019
 */
public interface IBaseStat<T extends IPokemon> extends IStatType, Nameable {

    int calculate(T pokemon);

}
