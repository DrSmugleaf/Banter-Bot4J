package com.github.drsmugleaf.pokemon.base.generation;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.PokemonGame;
import com.github.drsmugleaf.pokemon.base.external.Smogon;
import com.github.drsmugleaf.pokemon.base.format.FormatRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.type.TypeRegistry;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public abstract class BaseGeneration implements IGeneration {

    private final Smogon SMOGON;
    private final TypeRegistry TYPES;
    private final FormatRegistry FORMATS;

    protected BaseGeneration() {
        SMOGON = new Smogon(this);
        TYPES = new TypeRegistry(this);
        FORMATS = new FormatRegistry(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BaseGeneration)) return false;
        BaseGeneration that = (BaseGeneration) o;
        return getID() == that.getID();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getID());
    }

    @Nullable
    @Override
    public IGeneration getPrevious() {
        return PokemonGame.get().getGenerations().get(getID() - 1);
    }

    @Override
    public SortedSet<IGeneration> getAllPrevious() {
        SortedSet<IGeneration> generations = new TreeSet<>();
        IGeneration generation = this;
        while ((generation = generation.getPrevious()) != null) {
            generations.add(generation);
        }

        return generations;
    }

    @Nullable
    @Override
    public IGeneration getNext() {
        return PokemonGame.get().getGenerations().get(getID() + 1);
    }

    @Override
    public SortedSet<IGeneration> getAllNext() {
        SortedSet<IGeneration> generations = new TreeSet<>();
        IGeneration generation = this;
        while ((generation = generation.getNext()) != null) {
            generations.add(generation);
        }

        return generations;
    }

    @Override
    public Smogon getSmogon() {
        return SMOGON;
    }

    @Override
    public TypeRegistry getTypes() {
        return TYPES;
    }

    @Override
    public FormatRegistry getFormats() {
        return FORMATS;
    }

    @Override
    public int getTotalPokemon() {
        return getNewPokemon() + (getPrevious() == null ? 0 : getPrevious().getTotalPokemon());
    }

}
