package com.github.drsmugbrain.pokemon.types;

import com.github.drsmugbrain.pokemon.Action;
import com.github.drsmugbrain.pokemon.IBattle;
import com.github.drsmugbrain.pokemon.Move;
import com.github.drsmugbrain.pokemon.Pokemon;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 11/10/2017.
 */
public interface ITypes extends IBattle {

    @Nonnull
    List<Type> TYPES = new ArrayList<>();

    @Nonnull
    default List<Type> getTypes() {
        return new ArrayList<>(TYPES);
    }

    default boolean isType(@Nonnull Type... types) {
        for (Type type : types) {
            if (!TYPES.contains(type)) {
                return false;
            }
        }

        return true;
    }

    default void setTypes(@Nonnull Collection<Type> types) {
        TYPES.clear();
        TYPES.addAll(types);
    }

    default void setTypes(@Nonnull Type... types) {
        TYPES.clear();
        Collections.addAll(TYPES, types);
    }

    default boolean isImmune(@Nonnull Move move) {
        Type moveType = move.getType();

        for (Type type : TYPES) {
            if (type.getImmunities().contains(moveType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    default double damageMultiplier(@Nonnull Pokemon attacker, @Nonnull Action action) {
        Type attackType = action.getType();

        double modifier = 1.0;
        for (Type type : TYPES) {
            if (type.getImmunities().contains(attackType)) {
                return 0.0;
            }

            if (type.getWeaknesses().contains(attackType)) {
                modifier *= 2;
            }

            if (type.getResistances().contains(attackType)) {
                modifier /= 2;
            }
        }

        return modifier;
    }

}
