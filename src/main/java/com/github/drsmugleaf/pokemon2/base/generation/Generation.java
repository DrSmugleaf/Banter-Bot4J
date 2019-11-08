package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.pokemon2.base.external.Smogon;
import com.github.drsmugleaf.pokemon2.base.type.TypeRegistry;
import com.github.drsmugleaf.pokemon2.generations.vii.GenerationVII;
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

    @Contract(pure = true)
    public static IGeneration getLatestGeneration() {
        return GenerationVII.get();
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
