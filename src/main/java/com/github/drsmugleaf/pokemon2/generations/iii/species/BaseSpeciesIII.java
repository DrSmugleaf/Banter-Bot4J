package com.github.drsmugleaf.pokemon2.generations.iii.species;

import com.github.drsmugleaf.pokemon2.base.species.Species;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.IAbility;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public abstract class BaseSpeciesIII<T extends ISpeciesIII<T>> extends Species<T> implements ISpeciesIII<T> {

    private final ImmutableSet<IAbility> ABILITIES;

    protected BaseSpeciesIII(ISpeciesBuilderIII<T, ?> builder) {
        super(builder);
        ABILITIES = builder.getAbilities();
    }

    public ImmutableSet<IAbility> getAbilities() {
        return ABILITIES;
    }

}
