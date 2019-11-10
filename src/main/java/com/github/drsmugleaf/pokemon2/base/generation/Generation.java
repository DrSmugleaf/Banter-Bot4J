package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.pokemon2.base.external.Smogon;
import com.github.drsmugleaf.pokemon2.base.species.type.TypeRegistry;
import com.github.drsmugleaf.pokemon2.generations.i.GenerationI;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public abstract class Generation implements IGeneration {

    private static final GenerationRegistry GENERATIONS = new GenerationRegistry();

    private final Smogon SMOGON;
    private final TypeRegistry TYPES;

    protected Generation() {
        SMOGON = new Smogon(this);
        TYPES = new TypeRegistry(this);
    }

    @Contract(pure = true)
    public static GenerationRegistry getGenerations() {
        return GENERATIONS;
    }

    public static IGeneration getLatest() {
        IGeneration latest = GenerationI.get();
        for (IGeneration generation : GENERATIONS.get().values()) {
            if (generation.getID() > latest.getID()) {
                latest = generation;
            }
        }

        return latest;
    }

    public IGeneration getPrevious() {
        for (IGeneration generation : GENERATIONS.get().values()) {
            if (generation.getID() == getID() - 1) {
                return generation;
            }
        }

        return this;
    }

    public IGeneration getNext() {
        for (IGeneration generation : GENERATIONS.get().values()) {
            if (generation.getID() == getID() + 1) {
                return generation;
            }
        }

        return this;
    }

    @Override
    public Smogon getSmogon() {
        return SMOGON;
    }

    @Override
    public TypeRegistry getTypes() {
        return TYPES;
    }

}
