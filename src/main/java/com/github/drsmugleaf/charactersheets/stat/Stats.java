package com.github.drsmugleaf.charactersheets.stat;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Stats {

    private final ImmutableMap<String, Stat> STATS;

    public Stats(Map<String, Stat> stats) {
        STATS = ImmutableMap.copyOf(stats);
    }

    public ImmutableMap<String, Stat> getStats() {
        return STATS;
    }

    public Stat get(String name) {
        return STATS.get(name);
    }

}
