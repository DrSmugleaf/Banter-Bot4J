package com.github.drsmugleaf.pokemon2.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IBaseStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
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
    private final ImmutableMap<IBaseStat, IStat> STATS;
    private int hp;
    private double weight;
    private boolean isAlive;

    public BasePokemon(
            T species,
            @Nullable String nickname,
            Set<IType> types,
            @Nullable IItem item,
            IGender gender,
            int level,
            Map<IBaseStat, IStat> stats,
            int hp
    ) {
        SPECIES = species;
        NICKNAME = nickname;
        TYPES = new HashSet<>(types);
        this.item = item;
        GENDER = gender;
        LEVEL = level;
        STATS = ImmutableMap.copyOf(stats);
        this.hp = hp;
        weight = species.getWeight();
        isAlive = true;
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
    public ImmutableMap<IBaseStat, IStat> getStats() {
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

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void damage(int amount) {
        hp -= amount;

        if (hp <= 0) {
            hp = 0;
            isAlive = false;
        }
    }

}
