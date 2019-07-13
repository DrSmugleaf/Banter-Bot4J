package com.github.drsmugleaf.charactersheets.stat;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.charactersheets.Nameable;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class StatGroup implements Nameable {

    private final String NAME;
    private final ImmutableMap<String, Stat> STATS;

    public StatGroup(String name, Map<String, Stat> stats) {
        NAME = name;
        STATS = ImmutableMap.copyOf(stats);
    }

    @Override
    public String getName() {
        return NAME;
    }

    public ImmutableMap<String, Stat> get() {
        return STATS;
    }

    @Nullable
    public Stat get(String name) {
        return STATS.get(name);
    }

}
