package com.github.drsmugleaf.pokemon.base.pokemon.stat.type;

import com.github.drsmugleaf.pokemon.base.registry.Registry;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public class StatTypeRegistry extends Registry<IStatType> {

    public StatTypeRegistry(IStatType... stats) {
        super(stats);
    }

}
