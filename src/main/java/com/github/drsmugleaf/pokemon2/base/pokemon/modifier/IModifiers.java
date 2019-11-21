package com.github.drsmugleaf.pokemon2.base.pokemon.modifier;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;

/**
 * Created by DrSmugleaf on 20/11/2019
 */
public interface IModifiers {

    IModifierGroup<IPokemonState, IAllowedModifier, Boolean> getAllowed();
    IModifierGroup<IPokemonState, IExecutableModifier, Void> getExecutable();
    IModifierGroup<IPokemonState, IMultiplierModifier, Double> getMultiplier();
    boolean hasModifier(String name);
    boolean hasModifier(Nameable nameable);
    void removeAll(String name);
    void removeAll(Nameable nameable);

}
