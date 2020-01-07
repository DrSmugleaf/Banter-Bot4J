package com.github.drsmugleaf.pokemon.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.base.format.FormatRegistry;
import com.github.drsmugleaf.pokemon.base.generation.GenerationRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.IStatHandler;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon.base.pokemon.type.TypeRegistry;
import com.github.drsmugleaf.pokemon.generations.ii.item.IItem;
import com.github.drsmugleaf.pokemon.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IPokemon<T extends IPokemon<T>> extends ISpecies {

    ISpecies getSpecies();
    @Nullable String getNickname();
    String getDisplayName();
    ImmutableSet<IType> getTypes();
    void setTypes(Collection<IType> types);
    @Nullable IItem getItem();
    IGender getGender();
    int getLevel();
    IStatHandler<T> getStats();
    int getMaxHP();
    int getHP();
    double getWeight(); // Kilograms
    boolean isAlive();
    void faint();
    void damage(int amount);
    void damage(double percentage);
    void heal(int amount);

    @Override
    default GenerationRegistry getGenerations() {
        return getSpecies().getGenerations();
    }

    @Override
    default TypeRegistry getSpeciesTypes() {
        return getSpecies().getSpeciesTypes();
    }

    @Override
    default FormatRegistry getTiers() {
        return getSpecies().getTiers();
    }

    @Override
    default ImmutableMap<IStatType, Integer> getSpeciesStats() {
        return getSpecies().getSpeciesStats();
    }

    @Override
    default Pokedex<? extends ISpecies> getEvolutions() {
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
