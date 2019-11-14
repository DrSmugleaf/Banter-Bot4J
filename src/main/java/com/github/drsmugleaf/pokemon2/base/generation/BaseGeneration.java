package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.PokemonGame;
import com.github.drsmugleaf.pokemon2.base.external.Smogon;
import com.github.drsmugleaf.pokemon2.base.format.FormatRegistry;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.TypeRegistry;

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
    public int getTotalPokemons() {
        return getNewPokemons() + (getPrevious() == null ? 0 : getPrevious().getTotalPokemons());
    }

}
