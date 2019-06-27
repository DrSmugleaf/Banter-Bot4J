package com.github.drsmugleaf.pokemon.moves;

import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.Generation;
import com.github.drsmugleaf.pokemon.battle.InvalidGenerationException;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 18/06/2017.
 */
public enum CriticalHitStage {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4);

    public final int INDEX;

    CriticalHitStage(int index) {
        Holder.MAP.put(index, this);
        INDEX = index;
    }

    public static CriticalHitStage getStage(int index) {
        if (index <= 0) {
            return Holder.MAP.get(0);
        }

        if (index >= 4) {
            return Holder.MAP.get(4);
        }

        if (!Holder.MAP.containsKey(index)) {
            throw new NullPointerException("CriticalHitStage " + index + " doesn't exist");
        }

        return Holder.MAP.get(index);
    }

    // Parser usage only
    public static CriticalHitStage getStage(Generation generation, double percentage) {
        for (CriticalHitStage stage : CriticalHitStage.values()) {
            double generationPercentage = generation.getCriticalPercentage(stage);
            if (generationPercentage == percentage) {
                return stage;
            }
        }

        throw new NullPointerException("CriticalHitStage with percentage " + percentage + " doesn't exist");
    }

    public double getPercentage(Action action) {
        Generation generation = action.getGeneration();
        return generation.getCriticalPercentage(this);
    }

    public static double getProbability(Action action) {
        Generation generation = action.getGeneration();
        Pokemon attacker = action.getAttacker();
        BaseMove move = action.BASE_MOVE;

        switch (generation) {
            case I:
                // TODO: Lowering critical hit chance with focus energy and dire hit
                // TODO: Pokemon Stadium calculations
                int baseSpeed = attacker.STATS.get(PermanentStat.SPEED).getBase(attacker.getSpecies());
                double multiplier = 1.0;

                switch (move) {
                    case CRABHAMMER:
                    case KARATE_CHOP:
                    case RAZOR_LEAF:
                    case SLASH:
                        multiplier *= 8;
                }

                int threshold = (int) (baseSpeed / 2 * multiplier);
                double probability = threshold / 256.0;

                if (probability > 255) {
                    probability = 255;
                }

                return probability / 256.0;
            case II:
            case III:
            case IV:
            case V:
            case VI:
            case VII:
                // TODO: An attacking move will start out at stage 0, but there are several ways to increase a move's stage as detailed in the table below. An effect cannot stack with another effect in the same column, including itself.
                int attackerStage = attacker.getCriticalHitStage().INDEX;
                int moveStage = action.BASE_MOVE.BASE_CRITICAL_HIT_RATE.INDEX;
                int sumStageIndex = attackerStage + moveStage;
                CriticalHitStage sumStage = getStage(sumStageIndex);

                return sumStage.getPercentage(action);
            default:
                throw new InvalidGenerationException(generation);
        }
    }

    public static boolean isCritical(Action action) {
        if (action.hasTags(DamageTags.NO_CRITICAL)) {
            return false;
        }

        double random = ThreadLocalRandom.current().nextInt(100);
        double probability = getProbability(action);

        return random < probability;
    }

    private static class Holder {
        static Map<Integer, CriticalHitStage> MAP = new HashMap<>();
    }

}
