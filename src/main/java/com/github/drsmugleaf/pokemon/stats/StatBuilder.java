package com.github.drsmugleaf.pokemon.stats;

import java.util.*;

/**
 * Created by DrSmugleaf on 08/10/2017.
 */
public class StatBuilder {

    public static final List<IStat> STAT_LIST = new ArrayList<>();

    private final Map<IStat, Stat> STATS = new LinkedHashMap<>();

    static {
        Collections.addAll(STAT_LIST, PermanentStat.values());
        Collections.addAll(STAT_LIST, BattleStat.values());
    }

    public StatBuilder() {}

    private void finalizeStats() {
        for (IStat stat : STAT_LIST) {
            if (!STATS.containsKey(stat)) {
                STATS.put(stat, new Stat(stat, 31, 0));
            }
        }

        sortStats();
    }

    private void sortStats() {
        Map<IStat, Stat> stats = new LinkedHashMap<>();

        for (IStat stat : STAT_LIST) {
            stats.put(stat, STATS.get(stat));
        }

        STATS.clear();
        STATS.putAll(stats);
    }

    protected Map<IStat, Stat> build() {
        finalizeStats();
        return STATS;
    }

    public void put(IStat stat) {
        STATS.put(stat, new Stat(stat, 31, 0));
    }

    public void put(Stat stat) {
        STATS.put(stat.STAT, stat);
    }

    public void setIV(IStat stat, int iv) {
        if (!STATS.containsKey(stat)) {
            STATS.put(stat, new Stat(stat, iv, 0));
            return;
        }

        Stat mapStat = STATS.get(stat);
        STATS.put(stat, new Stat(stat, mapStat.IV, mapStat.IV));
    }

    public void setEV(IStat stat, int ev) {
        if (!STATS.containsKey(stat)) {
            STATS.put(stat, new Stat(stat, 31, ev));
            return;
        }

        Stat mapStat = STATS.get(stat);
        STATS.put(stat, new Stat(stat, mapStat.IV, mapStat.EV));
    }

}
