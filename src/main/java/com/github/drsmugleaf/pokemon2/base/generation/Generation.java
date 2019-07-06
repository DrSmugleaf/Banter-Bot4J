package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.pokemon2.base.external.Smogon;
import com.github.drsmugleaf.pokemon2.base.type.TypeRegistry;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public abstract class Generation implements IGeneration {

    private final Smogon SMOGON;
    private final TypeRegistry TYPES = new TypeRegistry(this);

    protected Generation() {
        SMOGON = new Smogon(this);
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
