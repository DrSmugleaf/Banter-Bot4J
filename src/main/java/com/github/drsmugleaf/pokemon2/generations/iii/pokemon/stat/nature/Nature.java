package com.github.drsmugleaf.pokemon2.generations.iii.pokemon.stat.nature;

import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IBaseStat;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class Nature implements INature {

    private final String NAME;
    private final ImmutableMap<IBaseStat, Double> MULTIPLIERS;

    public Nature(String name, Map<IBaseStat, Double> multipliers) {
        NAME = name;
        MULTIPLIERS = ImmutableMap.copyOf(multipliers);
    }

    @Override
    public double getStatMultiplier(IBaseStat stat) {
        return MULTIPLIERS.getOrDefault(stat, 1.0);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
