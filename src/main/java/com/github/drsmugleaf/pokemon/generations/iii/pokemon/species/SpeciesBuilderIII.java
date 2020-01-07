package com.github.drsmugleaf.pokemon.generations.iii.pokemon.species;

import com.github.drsmugleaf.pokemon.base.builder.IBuilder;
import com.github.drsmugleaf.pokemon.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon.base.pokemon.species.ISpeciesBuilder;
import com.github.drsmugleaf.pokemon.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon.base.pokemon.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.ability.IAbility;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by DrSmugleaf on 08/07/2019
 */
public class SpeciesBuilderIII<T extends ISpecies> extends SpeciesBuilder<T, SpeciesBuilderIII<T>> implements ISpeciesBuilderIII<T, SpeciesBuilderIII<T>>, IBuilder<SpeciesBuilderIII<T>> {

    private Set<IAbility> abilities = new HashSet<>();
    private Pokedex<T> evolutions; // TODO: 06-Jul-19 Add valid evolutions

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
    public Pokedex<T> getEvolutions() {
        return evolutions;
    }

    @Override
    public SpeciesBuilderIII<T> setEvolutions(Collection<T> evolutions) {
        this.evolutions = new Pokedex<>(evolutions);
        return this;
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
