package com.github.drsmugleaf.pokemon2.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.format.IFormat;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IStat;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon2.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon2.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IPokemon<T extends IPokemon<T>> extends ISpecies<T> {

    ISpecies<T> getSpecies();
    String getDisplayName();
    ImmutableSet<IType> getTypes();
    void setTypes(Collection<IType> types);
    @Nullable
    IItem getItem();
    IGender getGender();
    int getLevel();
    ImmutableMap<IStatType, IStat<T>> getStats();
    int getHP();
    double getWeight(); // Kilograms
    boolean isAlive();
    void damage(int amount);
    @Override
    default ImmutableSet<String> getGenerations() {
        return getSpecies().getGenerations();
    }
    @Override
    default ImmutableSet<IType> getSpeciesTypes() {
        return getSpecies().getSpeciesTypes();
    }
    @Override
    default ImmutableSet<IFormat> getTier() {
        return getSpecies().getTier();
    }
    @Override
    default ImmutableMap<IStatType, Integer> getSpeciesStats() {
        return getSpecies().getSpeciesStats();
    }
    @Override
    default ImmutableSet<T> getEvolutions() {
        return getSpecies().getEvolutions();
    }
    @Override
    default double getHeight() {
        return getSpecies().getHeight();
    }
    @Override
    default ImmutableSet<IGender> getGenders() {
        return getSpecies().getGenders();
    }
    @Override
    default ImmutableSet<String> getAlts() {
        return getSpecies().getAlts();
    }
    @Override
    default String getName() {
        return getSpecies().getName();
    }

}
