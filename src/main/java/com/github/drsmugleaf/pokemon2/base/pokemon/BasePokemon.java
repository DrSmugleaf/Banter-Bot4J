package com.github.drsmugleaf.pokemon2.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.stats.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public abstract class BasePokemon<T extends ISpecies<T>> implements IPokemon<T> {

    private final T SPECIES;
    @Nullable
    private final String NICKNAME;
    private final Set<IType> TYPES;
    @Nullable
    private IItem item;
    private final IGender GENDER;
    private final int LEVEL;
    private final ImmutableSet<IStat> STATS;
    private int hp;
    private double weight;

    public BasePokemon(
            T species,
            @Nullable String nickname,
            Set<IType> types,
            @Nullable IItem item,
            IGender gender,
            int level,
            Set<IStat> stats,
            int hp
    ) {
        SPECIES = species;
        NICKNAME = nickname;
        TYPES = new HashSet<>(types);
        this.item = item;
        GENDER = gender;
        LEVEL = level;
        STATS = ImmutableSet.copyOf(stats);
        this.hp = hp;
        weight = species.getWeight();
    }

    @Override
    public T getSpecies() {
        return SPECIES;
    }

    @Override
    public String getDisplayName() {
        return NICKNAME == null ? SPECIES.getName() : NICKNAME;
    }

    @Override
    public Set<IType> getTypes() {
        return TYPES;
    }

    @Override
    public void setTypes(Collection<IType> types) {
        TYPES.clear();
        TYPES.addAll(types);
    }

    @Nullable
    @Override
    public IItem getItem() {
        return item;
    }

    @Override
    public IGender getGender() {
        return GENDER;
    }

    @Override
    public int getLevel() {
        return LEVEL;
    }

    @Override
    public ImmutableSet<IStat> getStats() {
        return STATS;
    }

    @Override
    public int getHP() {
        return hp;
    }

    @Override
    public double getWeight() {
        return weight;
    }

}
