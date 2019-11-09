package com.github.drsmugleaf.pokemon2.base.species.stats;

import com.github.drsmugleaf.pokemon2.base.registry.Registry;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public class StatRegistry extends Registry<IStat> {

    public StatRegistry(IStat... stats) {
        super(ImmutableSet.copyOf(stats));
    }

}
