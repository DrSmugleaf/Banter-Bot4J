package com.github.drsmugleaf.charactersheets.stat;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.charactersheets.Builder;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DrSmugleaf on 12/07/2019
 */
public class StatsBuilder implements Builder<StatGroup> {

    @Nullable
    private String NAME;
    private Map<String, Stat> STATS = new TreeMap<>();

    public StatsBuilder() {}

    public StatsBuilder(StatGroup statGroup) {
        NAME = statGroup.getName();
        STATS.putAll(statGroup.get());
    }

    @Nullable
    public String getName() {
        return NAME;
    }

    public StatsBuilder setName(String name) {
        NAME = name;
        return this;
    }

    public Map<String, Stat> getStats() {
        return STATS;
    }

    public StatsBuilder addStat(Stat stat) {
        STATS.put(stat.getName(), stat);
        return this;
    }

    public StatsBuilder setStats(Map<String, Stat> stats) {
        STATS = stats;
        return this;
    }

    @Override
    public StatGroup build() {
        return new StatGroup(NAME, STATS);
    }

}
