package com.github.drsmugleaf.pokemon2.base.pokemon.stat.base;

import com.github.drsmugleaf.pokemon2.base.registry.Registry;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public class BaseStatRegistry extends Registry<IStatType> {

    public BaseStatRegistry(IStatType... stats) {
        super(stats);
    }

}
