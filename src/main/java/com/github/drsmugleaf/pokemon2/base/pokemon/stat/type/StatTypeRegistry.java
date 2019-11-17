package com.github.drsmugleaf.pokemon2.base.pokemon.stat.type;

import com.github.drsmugleaf.pokemon2.base.registry.Registry;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public class StatTypeRegistry extends Registry<IStatType> {

    public StatTypeRegistry(IStatType... stats) {
        super(stats);
    }

}
