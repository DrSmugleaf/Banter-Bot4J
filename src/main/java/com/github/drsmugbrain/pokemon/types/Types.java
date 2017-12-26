package com.github.drsmugbrain.pokemon.types;

import com.github.drsmugbrain.pokemon.battle.Action;
import com.github.drsmugbrain.pokemon.IModifier;
import com.github.drsmugbrain.pokemon.moves.Move;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by DrSmugleaf on 12/10/2017.
 */
public class Types implements IModifier {

    @Nonnull
    private final List<Type> TYPES = new ArrayList<>();

    public Types(@Nonnull List<Type> types) {
        TYPES.addAll(types);
    }

    @Nonnull
    public List<Type> getTypes() {
        return new ArrayList<>(TYPES);
    }

    public boolean isType(@Nonnull Type... types) {
        for (Type type : types) {
            if (!TYPES.contains(type)) {
                return false;
            }
        }

        return true;
    }

    public void setTypes(@Nonnull Collection<Type> types) {
        TYPES.clear();
        TYPES.addAll(types);
    }

    public void setTypes(@Nonnull Type... types) {
        setTypes(Arrays.asList(types));
    }

    public boolean isImmune(@Nonnull Move move) {
        Type moveType = move.getType();

        for (Type type : TYPES) {
            if (type.getImmunities().contains(moveType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public double damageMultiplier(@Nonnull Action action) {
        Type moveType = action.getType();

        double modifier = 1.0;
        for (Type type : TYPES) {
            if (type.getImmunities().contains(moveType)) {
                return 0.0;
            }

            if (type.getWeaknesses().contains(moveType)) {
                modifier *= 2;
            }

            if (type.getResistances().contains(moveType)) {
                modifier /= 2;
            }
        }

        return modifier;
    }

    @Nonnull
    public String[] getTypesString() {
        List<String> types = new ArrayList<>();

        for (Type type : TYPES) {
            types.add(type.getName());
        }

        return types.toArray(new String[types.size()]);
    }

}
