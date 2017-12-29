package com.github.drsmugleaf.pokemon.moves;

import javax.annotation.Nonnull;
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

    public final int INDEX;
    public final double PERCENTAGE;

    CriticalHitStage(int index, double percentage) {
        Holder.MAP.put(index, this);
        INDEX = index;
        PERCENTAGE = percentage;
    }

    @Nonnull
    public static CriticalHitStage getStage(int index) {
        if (index <= 0) return Holder.MAP.get(0);
        if (index >= 4) return Holder.MAP.get(4);

        if (!Holder.MAP.containsKey(index)) {
            throw new NullPointerException("CriticalHitStage " + index + " doesn't exist");
        }

        return Holder.MAP.get(index);
    }

    @Nonnull
    public static CriticalHitStage getStage(double percentage) {
        for (CriticalHitStage criticalHitStage : CriticalHitStage.values()) {
            if (criticalHitStage.PERCENTAGE == percentage) return criticalHitStage;
        }

        throw new NullPointerException("CriticalHitStage with percentage " + percentage + " doesn't exist");
    }

//    public static CriticalHitStage getStage(Pokemon pokemon, BaseMove move) {
//        return CriticalHitStage.getStage(move.getBaseCriticalHitRate().getStage() + pokemon.getCriticalHitStage().getStage());
//    }

    private static class Holder {
        static Map<Integer, CriticalHitStage> MAP = new HashMap<>();
    }

}
