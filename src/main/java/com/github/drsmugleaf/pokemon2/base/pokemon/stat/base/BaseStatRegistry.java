package com.github.drsmugleaf.pokemon2.base.pokemon.stat.base;

import com.github.drsmugleaf.pokemon2.base.registry.Registry;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public class BaseStatRegistry extends Registry<IBaseStat> {

    public BaseStatRegistry(IBaseStat... stats) {
        super(stats);
    }

}
