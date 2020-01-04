package com.github.drsmugleaf.pokemon.base.pokemon.modifier;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;

/**
 * Created by DrSmugleaf on 20/11/2019
 */
public class NamedModifier<T> implements INamedModifier<T> {

    private final String NAME;
    private final IModifier<T> MODIFIER;

    public NamedModifier(String name, IModifier<T> modifier) {
        NAME = name;
        MODIFIER = modifier;
    }

    public NamedModifier(Nameable nameable, IModifier<T> modifier) {
        this(nameable.getName(), modifier);
    }

    @Override
    public T run() {
        return MODIFIER.run();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
