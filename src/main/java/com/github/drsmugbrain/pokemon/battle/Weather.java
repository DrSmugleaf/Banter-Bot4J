package com.github.drsmugbrain.pokemon.battle;

import com.github.drsmugbrain.pokemon.IModifier;
import com.github.drsmugbrain.pokemon.moves.Action;
import com.github.drsmugbrain.pokemon.pokemon.Pokemon;
import com.github.drsmugbrain.pokemon.status.Status;
import com.github.drsmugbrain.pokemon.types.Type;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public enum Weather implements IModifier {

    SUNSHINE("Sunshine"),
    HARSH_SUNSHINE("Harsh Sunshine") {
        @Override
        public boolean canApplyStatus(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Status status) {
            if (status == Status.FREEZE) {
                return false;
            }

            return true;
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

    private final String NAME;

    Weather(@Nonnull String name) {
        this.NAME = name;
    }

}
