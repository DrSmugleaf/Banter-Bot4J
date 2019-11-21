package com.github.drsmugleaf.pokemon2.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.Species;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStatHandler;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.StatHandler;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.i.pokemon.stat.StatsI;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public abstract class Pokemon<T extends ISpecies> extends Species<T> implements IPokemon<T> {

    private final T SPECIES;
    @Nullable
    private final String NICKNAME;
    private final Set<IType> TYPES;
    @Nullable
    private IItem item;
    private final IGender GENDER;
    private final int LEVEL;
    private final IStatHandler<IPokemon<T>> STATS;
    private int MAX_HP;
    private int hp;
    private double weight;
    private boolean isAlive;

    public Pokemon(
            T species,
            @Nullable String nickname,
            Set<IType> types,
            @Nullable IItem item,
            IGender gender,
            int level,
            Map<IStatType, IStat<IPokemon<T>>> stats,
            int hp
    ) {
        super(species);
        SPECIES = species;
        NICKNAME = nickname;
        TYPES = new HashSet<>(types);
        this.item = item;
        GENDER = gender;
        LEVEL = level;
        STATS = new StatHandler<>(stats);
        MAX_HP = getStats().get(StatsI.HP).calculate(this);
        this.hp = hp;
        weight = species.getWeight();
        isAlive = true;
    }

    public Pokemon(IPokemon<T> pokemon) {
        this(
                pokemon.getSpecies(),
                pokemon.getNickname(),
                pokemon.getTypes(),
                pokemon.getItem(),
                pokemon.getGender(),
                pokemon.getLevel(),
                pokemon.getStats().get(),
                pokemon.getHP()
        );

        MAX_HP = pokemon.getMaxHP();
        weight = pokemon.getWeight();
        isAlive = pokemon.isAlive();
    }

    @Override
    public T getSpecies() {
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
    public IStatHandler<IPokemon<T>> getStats() {
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
