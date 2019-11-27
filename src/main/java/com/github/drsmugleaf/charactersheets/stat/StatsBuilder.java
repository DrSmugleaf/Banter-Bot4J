package com.github.drsmugleaf.charactersheets.stat;

import com.github.drsmugleaf.charactersheets.Builder;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
public class StatsBuilder implements Builder<StatGroup> {

    private final String NAME;
    private final Map<String, Stat> STATS = new TreeMap<>();

    public StatsBuilder(String name) {
        NAME = name;
    }

    public String getName() {
        return NAME;
    }

    public ImmutableMap<String, Stat> getStats() {
        return ImmutableMap.copyOf(STATS);
    }

    public StatsBuilder addStat(Stat stat) {
        STATS.put(stat.getName(), stat);
        return this;
    }

    public StatsBuilder setStats(Map<String, Stat> stats) {
        STATS.clear();
        STATS.putAll(stats);
        return this;
    }

    @Override
    public StatGroup build() {
        return new StatGroup(NAME, STATS);
    }

}
