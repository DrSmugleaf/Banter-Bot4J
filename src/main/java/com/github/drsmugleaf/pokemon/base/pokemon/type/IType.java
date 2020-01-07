package com.github.drsmugleaf.pokemon.base.pokemon.type;

import com.github.drsmugleaf.pokemon.base.registry.Columns;
import com.github.drsmugleaf.pokemon.base.registry.IColumns;
import com.github.drsmugleaf.pokemon.base.registry.IEntry;
import com.google.common.collect.ImmutableSet;

import java.util.Map;

/**
 * Created by DrSmugleaf on 05/07/2019
 */
public interface IType extends IEntry {

    @Override
    default Map<String, String> export() {
        IColumns columns = new Columns();
        columns.put("name", getName());

        return columns.get();
    }

    ImmutableSet<String> getWeaknesses();
    ImmutableSet<String> getResistances();
    ImmutableSet<String> getImmunities();

    default boolean isWeak(IType type) {
        return getWeaknesses().contains(type.getName());
    }

    default boolean isSuperEffective(IType type) {
        return type.getWeaknesses().contains(getName());
    }

    default boolean isResistant(IType type) {
        return getResistances().contains(type.getName());
    }

    default boolean isNotVeryEffective(IType type) {
        return type.getResistances().contains(getName());
    }

    default boolean isImmune(IType type) {
        return getImmunities().contains(type.getName());
    }

    default boolean isNotEffective(IType type) {
        return type.getWeaknesses().contains(getName());
    }

}
