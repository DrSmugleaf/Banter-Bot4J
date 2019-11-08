package com.github.drsmugleaf.pokemon2.generations.iii.species;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.builder.IBuilder;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.ISpeciesBuilder;
import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.IAbility;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by DrSmugleaf on 08/07/2019
 */
public class SpeciesBuilderIII<T extends ISpecies> extends SpeciesBuilder<T, SpeciesBuilderIII<T>> implements ISpeciesBuilderIII<T, SpeciesBuilderIII<T>>, IBuilder<SpeciesBuilderIII<T>> {

    @Nullable
    private Set<IAbility> abilities;

    public SpeciesBuilderIII() {
        super();
    }

    public SpeciesBuilderIII(ISpeciesBuilder<T, ?> builder) {
        super(builder);
    }

    public SpeciesBuilderIII(ISpeciesBuilderIII<T, ?> builder) {
        super(builder);
        abilities = builder.getAbilities();
    }

    @Override
    public ImmutableSet<IAbility> getAbilities() {
        return ImmutableSet.copyOf(abilities);
    }

    @Override
    public SpeciesBuilderIII<T> setAbilities(Collection<IAbility> abilities) {
        this.abilities = new HashSet<>(abilities);
        return getThis();
    }

}
