package com.github.drsmugleaf.pokemon2.base.pokemon.modifier;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;

/**
 * Created by DrSmugleaf on 18/11/2019
 */
public class NamedAllowedModifier implements INamedAllowedModifier {

    private final String NAME;
    private final IAllowedModifier MODIFIER;

    public NamedAllowedModifier(String name, IAllowedModifier modifier) {
        NAME = name;
        MODIFIER = modifier;
    }

    public NamedAllowedModifier(Nameable nameable, IAllowedModifier modifier) {
        this(nameable.getName(), modifier);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean allowed() {
        return MODIFIER.allowed();
    }

}
