package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.pokemon2.base.external.Smogon;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon2.base.type.TypeRegistry;

import java.util.function.Function;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public abstract class Generation<T extends ISpecies> implements IGeneration {

    private final Smogon SMOGON;
    private final Pokedex<T> POKEDEX;
    private final TypeRegistry TYPES = new TypeRegistry(this);

    protected Generation(Function<SpeciesBuilder<T>, T> constructor) {
        SMOGON = new Smogon(this);
        POKEDEX = new Pokedex<>(this, constructor);
    }

    @Override
    public Smogon getSmogon() {
        return SMOGON;
    }

    @Override
    public Pokedex<T> getPokedex() {
        return POKEDEX;
    }

    @Override
    public TypeRegistry getTypes() {
        return TYPES;
    }

}
