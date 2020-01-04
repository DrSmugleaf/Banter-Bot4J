package com.github.drsmugleaf.pokemon.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.IStatHandler;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.StatHandler;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public abstract class Pokemon<T extends Pokemon<T, S>, S extends ISpecies> implements IPokemon<T> {

    private final S SPECIES;
    @Nullable
    private final String NICKNAME;
    private final Set<IType> TYPES;
    @Nullable
    private IItem item;
    private final IGender GENDER;
    private final int LEVEL;
    private final IStatHandler<T> STATS;
    private int MAX_HP;
    private int hp;
    private double weight;
    private boolean isAlive;

    public Pokemon(
            S species,
            @Nullable String nickname,
            Set<IType> types,
            @Nullable IItem item,
            IGender gender,
            int level,
            Map<IStatType, IStat<T>> stats,
            int hp,
            int maxHp
    ) {
        SPECIES = species;
        NICKNAME = nickname;
        TYPES = new HashSet<>(types);
        this.item = item;
        GENDER = gender;
        LEVEL = level;
        STATS = new StatHandler<>(stats);
        MAX_HP = maxHp;
        this.hp = hp;
        weight = species.getWeight();
        isAlive = true;
    }

    public Pokemon(T pokemon) {
        this(
                pokemon.getSpecies(),
                pokemon.getNickname(),
                pokemon.getTypes(),
                pokemon.getItem(),
                pokemon.getGender(),
                pokemon.getLevel(),
                pokemon.getStats().get(),
                pokemon.getHP(),
                pokemon.getMaxHP()
        );

        MAX_HP = pokemon.getMaxHP();
        weight = pokemon.getWeight();
        isAlive = pokemon.isAlive();
    }

    @Override
    public S getSpecies() {
        return SPECIES;
    }

    @Nullable
    @Override
    public String getNickname() {
        return NICKNAME;
    }

    @Override
    public String getDisplayName() {
        return NICKNAME == null ? getName() : NICKNAME;
    }

    @Override
    public ImmutableSet<IType> getTypes() {
        return ImmutableSet.copyOf(TYPES);
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
    public IStatHandler<T> getStats() {
        return STATS;
    }

    @Override
    public int getMaxHP() {
        return MAX_HP;
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
    public void faint() {
        damage(hp);
    }

    @Override
    public void damage(int amount) {
        if (amount > hp) {
            amount = hp;
        }

        hp -= amount;

        if (hp <= 0) {
            hp = 0;
            isAlive = false;
        }
    }

    @Override
    public void damage(double percentage) {
        int amount = (int) (getMaxHP() * percentage);
        damage(amount);
    }

    @Override
    public void heal(int amount) {
        if (hp + amount > getMaxHP()) {
            amount = getMaxHP() - hp;
        }

        hp += amount;
    }

}
