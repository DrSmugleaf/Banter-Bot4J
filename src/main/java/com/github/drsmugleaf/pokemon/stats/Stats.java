package com.github.drsmugleaf.pokemon.stats;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import org.jetbrains.annotations.NotNull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 12/10/2017.
 */
public class Stats {

    @NotNull
    private final Map<IStat, Stat> STATS = new LinkedHashMap<>();

    public Stats(@NotNull StatBuilder builder) {
        STATS.putAll(builder.build());
    }

    @NotNull
    public Stat get(@NotNull IStat stat) {
        return STATS.get(stat);
    }

    @NotNull
    public Map<IStat, Stage> getStages() {
        Map<IStat, Stage> stages = new LinkedHashMap<>();

        for (Stat stat : STATS.values()) {
            stages.put(stat.STAT, stat.getStage());
        }

        return stages;
    }

    public void setStages(@NotNull Map<IStat, Stage> stages) {
        stages.forEach(((iStat, stage) -> STATS.get(iStat).setStage(stage)));
    }

    public void setStages(@NotNull Pokemon pokemon) {
        setStages(pokemon.STATS.getStages());
    }

    public void raiseStages(int amount, @NotNull IStat... stats) {
        for (IStat stat : stats) {
            STATS.get(stat).raiseStage(amount);
        }
    }

    public void lowerStages(int amount, @NotNull IStat... stats) {
        raiseStages(-amount, stats);
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
