package com.github.drsmugleaf.pokemon.generations.iii.pokemon.species;

import com.github.drsmugleaf.pokemon.base.pokemon.species.BaseSpecies;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.ability.IAbility;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public abstract class BaseSpeciesIII<T extends BaseSpeciesIII<T>> extends BaseSpecies<T> implements ISpeciesIII {

    private final ImmutableSet<IAbility> ABILITIES;

    protected BaseSpeciesIII(ISpeciesBuilderIII<T, ?> builder) {
        super(builder);
        ABILITIES = builder.getAbilities();
    }

    public ImmutableSet<IAbility> getAbilities() {
        return ABILITIES;
    }

}
