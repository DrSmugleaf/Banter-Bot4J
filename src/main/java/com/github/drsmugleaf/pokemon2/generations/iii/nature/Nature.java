package com.github.drsmugleaf.pokemon2.generations.iii.nature;

import com.github.drsmugleaf.pokemon2.base.species.stat.IStat;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class Nature implements INature {

    private final String NAME;
    private final ImmutableMap<IStat, Double> MULTIPLIERS;

    public Nature(String name, Map<IStat, Double> multipliers) {
        NAME = name;
        MULTIPLIERS = ImmutableMap.copyOf(multipliers);
    }

    @Override
    public double getStatMultiplier(IStat stat) {
        return MULTIPLIERS.getOrDefault(stat, 1.0);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
