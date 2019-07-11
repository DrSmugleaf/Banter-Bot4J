package com.github.drsmugleaf.pokemon2.generations.iii.species;

import com.github.drsmugleaf.pokemon2.generations.ii.species.SpeciesII;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.IAbility;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class SpeciesIII<T extends SpeciesIII<T>> extends SpeciesII<T> {

    private final ImmutableSet<IAbility> ABILITIES;

    public SpeciesIII(SpeciesBuilderIII<T> builder) {
        super(builder);
        ABILITIES = builder.getAbilities();
    }

    public ImmutableSet<IAbility> getAbilities() {
        return ABILITIES;
    }

}
