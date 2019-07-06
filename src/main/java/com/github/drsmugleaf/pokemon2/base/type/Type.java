package com.github.drsmugleaf.pokemon2.base.type;

import com.google.common.collect.ImmutableSet;

import java.util.Collection;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class Type implements IType {

    private final String NAME;
    private final ImmutableSet<String> WEAKNESSES;
    private final ImmutableSet<String> RESISTANCES;
    private final ImmutableSet<String> IMMUNITIES;

    protected Type(String name, Collection<String> weaknesses, Collection<String> resistances, Collection<String> immunities) {
        NAME = name;
        WEAKNESSES = ImmutableSet.copyOf(weaknesses);
        RESISTANCES = ImmutableSet.copyOf(resistances);
        IMMUNITIES = ImmutableSet.copyOf(immunities);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ImmutableSet<String> getWeaknesses() {
        return WEAKNESSES;
    }

    @Override
    public ImmutableSet<String> getResistances() {
        return RESISTANCES;
    }

    @Override
    public ImmutableSet<String> getImmunities() {
        return IMMUNITIES;
    }

}
