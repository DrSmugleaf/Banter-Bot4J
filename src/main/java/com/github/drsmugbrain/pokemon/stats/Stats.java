package com.github.drsmugbrain.pokemon.stats;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 08/10/2017.
 */
public class Stats {

    @Nonnull
    private final Map<IStat, Stat> STATS = new LinkedHashMap<>();

    public Stats(@Nonnull StatBuilder builder) {
        STATS.putAll(builder.build());
    }

    @Nonnull
    public Stat get(@Nonnull IStat stat) {
        return STATS.get(stat);
    }

    @Nonnull
    public Map<IStat, Stage> getStages() {
        Map<IStat, Stage> stages = new LinkedHashMap<>();

        for (Stat stat : STATS.values()) {
            stages.put(stat.getIStat(), stat.getStage());
        }

        return stages;
    }

    public void setStages(Map<IStat, Stage> stages) {
        stages.forEach((iStat, stage) -> STATS.get(iStat).setStage(stage));
    }

    public void raiseStage(int amount, IStat... stats) {
        for (IStat stat : stats) {
            STATS.get(stat).raiseStage(amount);
        }
    }

    public void lowerStage(int amount, IStat... stats) {
        for (IStat stat : stats) {
            STATS.get(stat).lowerStage(amount);
        }
    }

    public void resetStages() {
        for (Stat stat : STATS.values()) {
            stat.resetStage();
        }
    }

    public void resetLoweredStages() {
        for (Stat stat : STATS.values()) {
            stat.resetLoweredStage();
        }
    }

}
