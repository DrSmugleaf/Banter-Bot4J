package com.github.drsmugleaf.pokemon2.generations.iii.pokemon.stat.nature;

import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class Nature implements INature {

    private final String NAME;
    private final ImmutableMap<IStatType, Double> MULTIPLIERS;

    public Nature(String name, Map<IStatType, Double> multipliers) {
        NAME = name;
        MULTIPLIERS = ImmutableMap.copyOf(multipliers);
    }

    @Override
    public double getStatMultiplier(IStatType stat) {
        return MULTIPLIERS.getOrDefault(stat, 1.0);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
