package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 18/06/2017.
 */
public enum CriticalHitStage {

    ZERO(0, 6.25),
    ONE(1, 12.5),
    TWO(2, 50),
    THREE(3, 100),
    FOUR(4, 100);

    private final int STAGE;
    private final double PERCENTAGE;

    CriticalHitStage(int stage, double percentage) {
        Holder.MAP.put(stage, this);
        this.STAGE = stage;
        this.PERCENTAGE = percentage;
    }

    @Nonnull
    public static CriticalHitStage getStage(int stage) {
        if (stage >= 4) return Holder.MAP.get(4);
        if (stage <= 0) return Holder.MAP.get(0);
        if (!Holder.MAP.containsKey(stage)) {
            throw new NullPointerException("CriticalHitStage " + stage + " doesn't exist");
        }

        return Holder.MAP.get(stage);
    }

    @Nullable
    public static CriticalHitStage getStageByPercentage(double percentage) {
        for (CriticalHitStage criticalHitStage : CriticalHitStage.values()) {
            if (criticalHitStage.PERCENTAGE == percentage) return criticalHitStage;
        }
        return null;
    }

//    public static CriticalHitStage getStage(Pokemon pokemon, Movess move) {
//        return CriticalHitStage.getStage(move.getBaseCriticalHitRate().getStage() + pokemon.getCriticalHitStage().getStage());
//    }

    public int getStage() {
        return this.STAGE;
    }

    public double getCriticalChance() {
        return this.PERCENTAGE;
    }

    private static class Holder {
        static Map<Integer, CriticalHitStage> MAP = new HashMap<>();
    }

}
