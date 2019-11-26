package com.github.drsmugleaf.pokemon2.base.pokemon.stat;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 19/11/2019
 */
public class StatHandler<T extends IPokemon<T>> implements IStatHandler<T> {

    private final ImmutableMap<IStatType, IStat<T>> STATS;

    public StatHandler(Map<IStatType, IStat<T>> stats) {
        STATS = ImmutableMap.copyOf(stats);
    }

    @Override
    public ImmutableMap<IStatType, IStat<T>> get() {
        return STATS;
    }

    @Override
    public IStat<T> get(IStatType type) {
        return STATS.get(type);
    }

}
