package com.github.drsmugbrain.pokemon;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 10/06/2017.
 */
public enum Stage {
    
    NEGATIVE_SIX(-6, 0.25, 0.33, 3),
    NEGATIVE_FIVE(-5, 0.285, 0.375, 2.66),
    NEGATIVE_FOUR(-4, 0.33, 0.428, 2.33),
    NEGATIVE_THREE(-3, 0.4, 0.5, 2),
    NEGATIVE_TWO(-2, 0.5, 0.6, 1.66),
    NEGATIVE_ONE(-1, 0.66, 0.75, 1.33),
    ZERO(0, 1, 1, 1),
    POSITIVE_ONE(1, 1.5, 1.33, 0.75),
    POSITIVE_TWO(2, 2, 1.66, 0.6),
    POSITIVE_THREE(3, 2.5, 2, 0.5),
    POSITIVE_FOUR(4, 3, 2.33, 0.428),
    POSITIVE_FIVE(5, 3.5, 2.66, 0.375),
    POSITIVE_SIX(6, 4, 3, 0.33);

    private final int STAGE;
    private final double MAIN_STAT_MULTIPLIER;
    private final double ACCURACY_MULTIPLIER;
    private final double EVASION_MULTIPLIER;

    Stage(int stage, double mainStatMultiplier, double accuracyMultiplier, double evasionMultiplier) {
        this.STAGE = stage;
        this.MAIN_STAT_MULTIPLIER = mainStatMultiplier;
        this.ACCURACY_MULTIPLIER = accuracyMultiplier;
        this.EVASION_MULTIPLIER = evasionMultiplier;
    }

    public int getStage() {
        return this.STAGE;
    }

    public double getStatMultiplier(Stat stat) {
        switch (stat) {
            case HP:case ATTACK:case DEFENSE:case SPEED:case SPECIAL_ATTACK:case SPECIAL_DEFENSE:
                return this.MAIN_STAT_MULTIPLIER;
            case ACCURACY:
                return this.ACCURACY_MULTIPLIER;
            case EVASION:
                return this.EVASION_MULTIPLIER;
            default:
                return 1.0;
        }
    }

    public double getMainStatMultiplier() {
        return this.MAIN_STAT_MULTIPLIER;
    }

    public double getAccuracyMultiplier() {
        return this.ACCURACY_MULTIPLIER;
    }

    public double getEvasionMultiplier() {
        return this.EVASION_MULTIPLIER;
    }

    public static Stage getStage(int stage) {
        if (!Holder.MAP.containsKey(stage)) {
            throw new NullPointerException("Stage " + stage + " doesn't exist");
        }

        return Holder.MAP.get(stage);
    }

    private static class Holder {
        static Map<Integer, Stage> MAP = new HashMap<>();
    }
    
}
