package com.github.drsmugleaf.pokemon.battle;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.status.Status;
import com.github.drsmugleaf.pokemon.types.Type;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public enum Weather implements IModifier {

    NONE("None"),
    SUNSHINE("Sunshine"),
    HARSH_SUNSHINE("Harsh Sunshine") {
        @Override
        public boolean canApplyStatus(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Status status) {
            return status != Status.FREEZE;
        }

        @Override
        public double damageMultiplier(@Nonnull Action action) {
            Type type = action.getType();

            if (type == Type.FIRE) {
                return 1.5;
            } else if (type == Type.WATER) {
                return 0.5;
            }

            return 1.0;
        }
    },
    RAIN("Rain") {
        @Override
        public double damageMultiplier(@Nonnull Action action) {
            Type type = action.getType();

            if (type == Type.FIRE) {
                return 0.5;
            } else if (type == Type.WATER) {
                return 1.5;
            }

            return 1.0;
        }
    },
    HEAVY_RAIN("Heavy Rain"),
    HAIL("Hail"),
    SANDSTORM("Sandstorm"),
    STRONG_WINDS("Strong Winds"),
    FOG("Fog"),
    SHADOW_SKY("Shadow Sky");

    @Nonnull
    public final String NAME;

    Weather(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
