package com.github.drsmugleaf.pokemon2.generations.iii.species;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.pokemon.Gender;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon2.base.type.IType;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.IAbility;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 08/07/2019
 */
public class SpeciesBuilderIII<T extends ISpecies> extends SpeciesBuilder<T> {

    @Nullable
    private Set<IAbility> abilities;
    private final Function<SpeciesBuilderIII<T>, T> CONSTRUCTOR;

    public SpeciesBuilderIII(Function<SpeciesBuilderIII<T>, T> constructor) {
        super();
        CONSTRUCTOR = constructor;
    }

    public SpeciesBuilderIII(SpeciesBuilder<T> builder, Function<SpeciesBuilderIII<T>, T> constructor) {
        super(builder);
        CONSTRUCTOR = constructor;
    }

    public SpeciesBuilderIII(SpeciesBuilderIII<T> builder) {
        super(builder);
        abilities = builder.abilities;
        CONSTRUCTOR = builder.CONSTRUCTOR;
    }

    public ImmutableSet<IAbility> getAbilities() {
        return ImmutableSet.copyOf(abilities);
    }

    public SpeciesBuilderIII<T> setAbilities(Collection<? extends IAbility> abilities) {
        this.abilities = new HashSet<>(abilities);
        return this;
    }

    @Override
    public SpeciesBuilderIII<T> setName(String name) {
        super.setName(name);
        return this;
    }

    @Override
    public SpeciesBuilderIII<T> setGenerations(Collection<String> generations) {
        super.setGenerations(generations);
        return this;
    }

    @Override
    public SpeciesBuilderIII<T> setTypes(Collection<? extends IType> types) {
        super.setTypes(types);
        return this;
    }

    @Override
    public SpeciesBuilderIII<T> setTiers(Collection<Tier> tiers) {
        super.setTiers(tiers);
        return this;
    }

    @Override
    public SpeciesBuilderIII<T> addStat(PermanentStat stat, Integer amount) {
        super.addStat(stat, amount);
        return this;
    }

    @Override
    public SpeciesBuilderIII<T> setStats(Map<PermanentStat, Integer> stats) {
        super.setStats(stats);
        return this;
    }

    @Override
    public SpeciesBuilderIII<T> setEvolutions(Collection<T> evolutions) {
        super.setEvolutions(evolutions);
        return this;
    }

    @Override
    public SpeciesBuilderIII<T> setWeight(double weight) {
        super.setWeight(weight);
        return this;
    }

    @Override
    public SpeciesBuilderIII<T> setHeight(double height) {
        super.setHeight(height);
        return this;
    }

    @Override
    public SpeciesBuilderIII<T> setGenders(Collection<Gender> genders) {
        super.setGenders(genders);
        return this;
    }

    @Override
    public SpeciesBuilderIII<T> setAlts(Collection<String> alts) {
        super.setAlts(alts);
        return this;
    }

}
