package com.github.drsmugbrain.pokemon.stats;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 11/10/2017.
 */
public interface IStats {

    @Nonnull
    Map<IStat, Stat> STATS = new LinkedHashMap<>();

    @Nonnull
    default Stat get(@Nonnull IStat stat) {
        return STATS.get(stat);
    }

    default int iv() {
        return 1;
    }

    @Nonnull
    default Map<IStat, Stage> getStages() {
        Map<IStat, Stage> stages = new LinkedHashMap<>();

        for (Stat stat : STATS.values()) {
            stages.put(stat.getIStat(), stat.getStage());
        }

        return stages;
    }

    default void setStages(@Nonnull Map<IStat, Stage> stages) {
        getStages().forEach(((iStat, stage) -> STATS.get(iStat).setStage(stage)));
    }

    default void setStages(@Nonnull IStats iStats) {
        setStages(iStats.getStages());
    }

    default void raiseStages(int amount, @Nonnull IStat... stats) {
        for (IStat stat : stats) {
            STATS.get(stat).raiseStage(amount);
        }
    }

    default void lowerStages(int amount, @Nonnull IStat... stats) {
        raiseStages(-amount, stats);
    }

    default void resetStages() {
        for (Stat stat : STATS.values()) {
            stat.resetStage();
        }
    }

    default void resetLoweredStages() {
        for (Stat stat : STATS.values()) {
            stat.resetLoweredStage();
        }
    }

    double calculate(@Nonnull IStat stat);

    double calculateWithoutStages(@Nonnull IStat stat);

}
