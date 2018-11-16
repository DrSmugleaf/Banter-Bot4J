package com.github.drsmugleaf.pokemon.types;

import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.IModifier;
import com.github.drsmugleaf.pokemon.moves.Move;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by DrSmugleaf on 12/10/2017.
 */
public class Types implements IModifier {

    @NotNull
    private final List<Type> TYPES = new ArrayList<>();

    public Types(@NotNull List<Type> types) {
        TYPES.addAll(types);
    }

    @NotNull
    public List<Type> getTypes() {
        return new ArrayList<>(TYPES);
    }

    public boolean isType(@NotNull Type... types) {
        for (Type type : types) {
            if (!TYPES.contains(type)) {
                return false;
            }
        }

        return true;
    }

    public void setTypes(@NotNull Collection<Type> types) {
        TYPES.clear();
        TYPES.addAll(types);
    }

    public void setTypes(@NotNull Type... types) {
        setTypes(Arrays.asList(types));
    }

    public boolean isImmune(@NotNull Move move) {
        Type moveType = move.getType();

        for (Type type : TYPES) {
            if (type.getImmunities().contains(moveType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public double damageMultiplier(@NotNull Action action) {
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

    @NotNull
    public String[] getTypesString() {
        List<String> types = new ArrayList<>();

        for (Type type : TYPES) {
            types.add(type.getName());
        }

        return types.toArray(new String[0]);
    }

}
