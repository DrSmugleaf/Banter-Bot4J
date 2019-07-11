package com.github.drsmugleaf.pokemon2.generations.iii.nature;

import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class Nature implements INature {

    private final String NAME;
    private final ImmutableMap<PermanentStat, Double> MULTIPLIERS;

    public Nature(String name, Map<PermanentStat, Double> multipliers) {
        NAME = name;
        MULTIPLIERS = ImmutableMap.copyOf(multipliers);
    }

    @Override
    public double getStatMultiplier(PermanentStat stat) {
        return MULTIPLIERS.get(stat);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
