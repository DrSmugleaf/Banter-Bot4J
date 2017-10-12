package com.github.drsmugbrain.pokemon.stats;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 08/10/2017.
 */
public class StatBuilder {

    public static final List<IStat> ISTATS = new ArrayList<>();
    private final Map<IStat, Stat> STATS = new LinkedHashMap<>();

    static {
        Collections.addAll(ISTATS, PermanentStat.values());
        Collections.addAll(ISTATS, BattleStat.values());
    }

    public StatBuilder() {}

    private void finalizeStats() {
        for (IStat stat : ISTATS) {
            if (!STATS.containsKey(stat)) {
                STATS.put(stat, new Stat(stat, 31, 0));
            }
        }

        sortStats();
    }

    private void sortStats() {
        Map<IStat, Stat> stats = new LinkedHashMap<>();

        for (IStat stat : ISTATS) {
            stats.put(stat, STATS.get(stat));
        }

        STATS.clear();
        STATS.putAll(stats);
    }

    protected Map<IStat, Stat> build() {
        finalizeStats();
        return STATS;
    }

    public void put(@Nonnull IStat stat) {
        STATS.put(stat, new Stat(stat, 31, 0));
    }

    public void put(@Nonnull Stat stat) {
        STATS.put(stat.getIStat(), stat);
    }

    public void setIV(@Nonnull IStat stat, int iv) {
        if (!STATS.containsKey(stat)) {
            STATS.put(stat, new Stat(stat, iv, 0));
            return;
        }

        Stat mapStat = STATS.get(stat);
        STATS.put(stat, new Stat(stat, mapStat.getIV(), mapStat.getIV()));
    }

    public void setEV(@Nonnull IStat stat, int ev) {
        if (!STATS.containsKey(stat)) {
            STATS.put(stat, new Stat(stat, 31, ev));
            return;
        }

        Stat mapStat = STATS.get(stat);
        STATS.put(stat, new Stat(stat, mapStat.getIV(), mapStat.getEV()));
    }

}
