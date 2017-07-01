package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public enum Weather {

    SUNSHINE("Sunshine"),
    HARSH_SUNSHINE("Harsh Sunshine") {
        @Override
        protected boolean tryApplyStatusEffect(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Status status) {
            switch (battle.getGeneration()) {
                case I:
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    if (status == Status.FREEZE) {
                        return false;
                    }
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            return super.tryApplyStatusEffect(user, target, battle, trainer, move, status);
        }
    },
    RAIN("Rain"),
    HEAVY_RAIN("Heavy Rain"),
    HAIL("Hail"),
    SANDSTORM("Sandstorm"),
    STRONG_WINDS("Strong Winds"),
    FOG("Fog"),
    SHADOW_SKY("Shadow Sky");

    private final String NAME;

    Weather(@Nonnull String name) {
        this.NAME = name;
    }

    protected boolean tryApplyStatusEffect(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Status status) {
        return true;
    }

}
