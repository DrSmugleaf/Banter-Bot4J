package com.github.drsmugleaf.pokemon.stats;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 10/06/2017.
 */
public enum Stage {
    
    NEGATIVE_SIX(-6, 0.25, 0.33, 0.33),
    NEGATIVE_FIVE(-5, 0.285, 0.375, 0.375),
    NEGATIVE_FOUR(-4, 0.33, 0.428, 0.428),
    NEGATIVE_THREE(-3, 0.4, 0.5, 0.5),
    NEGATIVE_TWO(-2, 0.5, 0.6, 0.6),
    NEGATIVE_ONE(-1, 0.66, 0.75, 0.75),
    ZERO(0, 1, 1, 1),
    POSITIVE_ONE(1, 1.5, 1.33, 1.33),
    POSITIVE_TWO(2, 2, 1.66, 1.66),
    POSITIVE_THREE(3, 2.5, 2, 2),
    POSITIVE_FOUR(4, 3, 2.33, 2.33),
    POSITIVE_FIVE(5, 3.5, 2.66, 2.66),
    POSITIVE_SIX(6, 4, 3, 3);

    public final int STAGE;
    public final double MAIN_STAT_MULTIPLIER;
    public final double ACCURACY_MULTIPLIER;
    public final double EVASION_MULTIPLIER;

    Stage(int stage, double mainStatMultiplier, double accuracyMultiplier, double evasionMultiplier) {
        Holder.MAP.put(stage, this);
        STAGE = stage;
        MAIN_STAT_MULTIPLIER = mainStatMultiplier;
        ACCURACY_MULTIPLIER = accuracyMultiplier;
        EVASION_MULTIPLIER = evasionMultiplier;
    }

    @Nonnull
    public static Stage getStage(int stage) {
        if (!Holder.MAP.containsKey(stage)) {
            throw new NullPointerException("Stage " + stage + " doesn't exist");
        }

        return Holder.MAP.get(stage);
    }

    public int getStage() {
        return STAGE;
    }

    public double getStatMultiplier(IStat stat) {
        if (stat instanceof PermanentStat) {
            return MAIN_STAT_MULTIPLIER;
        } else if (stat instanceof BattleStat) {
            if (stat == BattleStat.ACCURACY) {
                return ACCURACY_MULTIPLIER;
            } else if (stat == BattleStat.EVASION) {
                return EVASION_MULTIPLIER;
            }
        }

        throw new IllegalArgumentException("Invalid stat: " + stat);
    }

    public double getMainStatMultiplier() {
        return MAIN_STAT_MULTIPLIER;
    }

    public double getAccuracyMultiplier() {
        return ACCURACY_MULTIPLIER;
    }

    public double getEvasionMultiplier() {
        return EVASION_MULTIPLIER;
    }

    private static class Holder {
        @Nonnull
        static Map<Integer, Stage> MAP = new HashMap<>();
    }
    
}
