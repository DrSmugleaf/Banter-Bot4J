package com.github.drsmugleaf.charactersheets.stat;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Stats {

    private final Map<String, Stat> stats;

    public Stats(Map<String, Stat> stats) {
        this.stats = new TreeMap<>(stats);
    }

    public Stat get(String name) {
        return stats.get(name);
    }

}
