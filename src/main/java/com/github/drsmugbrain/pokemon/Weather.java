package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public enum Weather implements IBattle {

    SUNSHINE("Sunshine"),
    HARSH_SUNSHINE("Harsh Sunshine") {
        @Override
        public boolean canApplyStatus(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Status status) {
            if (status == Status.FREEZE) {
                return false;
            }

            return true;
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

}
