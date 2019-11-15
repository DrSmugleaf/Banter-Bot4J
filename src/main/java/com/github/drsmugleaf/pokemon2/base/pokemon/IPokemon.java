package com.github.drsmugleaf.pokemon2.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.base.IBaseStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.Set;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IPokemon<T extends ISpecies<T>> {

    T getSpecies();
    String getDisplayName();
    Set<IType> getTypes();
    void setTypes(Collection<IType> types);
    @Nullable
    IItem getItem();
    IGender getGender();
    int getLevel();
    ImmutableMap<IBaseStat, IStat> getStats();
    int getHP();
    double getWeight(); // Kilograms
    boolean isAlive();
    void damage(int amount);

}
