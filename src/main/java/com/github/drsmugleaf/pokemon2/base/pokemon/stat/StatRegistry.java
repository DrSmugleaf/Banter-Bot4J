package com.github.drsmugleaf.pokemon2.base.pokemon.stat;

import com.github.drsmugleaf.pokemon2.base.registry.Registry;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public class StatRegistry extends Registry<IStat> {

    public StatRegistry(IStat... stats) {
        super(stats);
    }

}
