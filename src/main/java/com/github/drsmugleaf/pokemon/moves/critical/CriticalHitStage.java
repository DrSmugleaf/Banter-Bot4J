package com.github.drsmugleaf.pokemon.moves.critical;

import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon.battle.Generation;
import com.github.drsmugleaf.pokemon.battle.InvalidGenerationException;
import com.github.drsmugleaf.pokemon.moves.DamageTags;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon.status.BaseVolatileStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 18/06/2017.
 */
public enum CriticalHitStage {

    ZERO(0) {
        @Override
        protected double getPercentage(Generation generation) {
            switch (generation) {
                case II:
                case III:
                case IV:
                case V:
                case VI:
                    return 6.25;
                case VII:
                    return 4.1666666667;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }
    },
    ONE(1) {
        @Override
        protected double getPercentage(Generation generation) {
            switch (generation) {
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    return 12.5;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }
    },
    TWO(2) {
        @Override
        protected double getPercentage(Generation generation) {
            switch (generation) {
                case II:
                case III:
                case IV:
                case V:
                    return 25.0;
                case VI:
                case VII:
                    return 50.0;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }
    },
    THREE(3) {
        @Override
        protected double getPercentage(Generation generation) {
            switch (generation) {
                case II:
                case III:
                case IV:
                case V:
                    return 33.3333333333;
                case VI:
                case VII:
                    return 100.0;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }
    },
    FOUR(4) {
        @Override
        protected double getPercentage(Generation generation) {
            switch (generation) {
                case II:
                case III:
                case IV:
                case V:
                    return 50.0;
                case VI:
                case VII:
                    return 100.0;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }
    };

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

    public static CriticalHitStage getStage(Pokemon attacker, Action action) {
        int attackerStage = attacker.getCriticalHitStage().INDEX;
        int moveStage = action.BASE_MOVE.BASE_CRITICAL_HIT_RATE.INDEX;
        int totalStage = attackerStage + moveStage;

        return getStage(totalStage);
    }

    // Parser usage only
    public static CriticalHitStage getStage(Generation generation, double percentage) {
        for (CriticalHitStage stage : CriticalHitStage.values()) {
            double generationPercentage = stage.getPercentage(generation);
            if (generationPercentage == percentage) {
                return stage;
            }
        }

        throw new NullPointerException("CriticalHitStage with percentage " + percentage + " doesn't exist");
    }

    public double getProbability(Action action) {
        Generation generation = action.getGeneration();
        Pokemon attacker = action.getAttacker();

        switch (generation) {
            case I:
                // TODO: Lowering critical hit chance with dire hit
                // TODO: Pokemon Stadium calculations
                int baseSpeed = attacker.STATS.get(PermanentStat.SPEED).getBase(attacker.getSpecies());
                double multiplier = 0.5;

                if (action.getBattle().getGame() == Game.STADIUM) {
                    baseSpeed += 76;
                    multiplier /= 2;

                    if (attacker.STATUSES.hasVolatileStatus(BaseVolatileStatus.FOCUS_ENERGY)) {
                        baseSpeed += 160;
                    }
                }

                if (action.hasTags(DamageTags.HIGH_CRITICAL_RATIO)) {
                    multiplier *= 8;
                }

                int threshold = (int) (baseSpeed * multiplier);

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
                return getPercentage(action.getGeneration());
            default:
                throw new InvalidGenerationException(generation);
        }
    }

    public static boolean isCritical(Action action) {
        if (action.hasTags(DamageTags.NO_CRITICAL)) {
            return false;
        }

        double random = ThreadLocalRandom.current().nextInt(100);
        CriticalHitStage stage = getStage(action.getAttacker(), action);
        double probability = stage.getProbability(action);

        return random < probability;
    }

    protected abstract double getPercentage(Generation generation);

    private static class Holder {
        static Map<Integer, CriticalHitStage> MAP = new HashMap<>();
    }

}
