package com.github.drsmugleaf.pokemon2.base.pokemon.modifier;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;

/**
 * Created by DrSmugleaf on 18/11/2019
 */
public class NamedModifier implements INamedModifier {

    private final String NAME;
    private final IModifier MODIFIER;

    public NamedModifier(String name, IModifier modifier) {
        NAME = name;
        MODIFIER = modifier;
    }

    public NamedModifier(Nameable nameable, IModifier modifier) {
        this(nameable.getName(), modifier);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean run() {
        return MODIFIER.run();
    }

}
