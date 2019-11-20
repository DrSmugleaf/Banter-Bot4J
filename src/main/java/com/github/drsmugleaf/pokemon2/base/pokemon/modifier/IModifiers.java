package com.github.drsmugleaf.pokemon2.base.pokemon.modifier;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;

/**
 * Created by DrSmugleaf on 20/11/2019
 */
public interface IModifiers {

    void addAllowed(IPokemonState state, Nameable nameable, IModifier<Boolean> multiplier);
    void addAllowedUnique(IPokemonState state, Nameable nameable, IModifier<Boolean> multiplier);
    ImmutableMultimap<IPokemonState, INamedModifier<Boolean>> getAllowed();
    ImmutableCollection<INamedModifier<Boolean>> getAllowed(IPokemonState state);
    void addMultiplier(IPokemonState state, Nameable nameable, IModifier<Double> multiplier);
    void addMultiplierUnique(IPokemonState state, Nameable nameable, IModifier<Double> multiplier);
    ImmutableMultimap<IPokemonState, INamedModifier<Double>> getMultiplier();
    ImmutableCollection<INamedModifier<Double>> getMultiplier(IPokemonState state);
    void addExecutable(IPokemonState state, Nameable nameable, IModifier<Void> executable);
    void addExecutableUnique(IPokemonState state, Nameable nameable, IModifier<Void> executable);
    ImmutableMultimap<IPokemonState, INamedModifier<Void>> getExecutable();
    ImmutableCollection<INamedModifier<Void>> getExecutable(IPokemonState state);
    boolean hasModifier(String name);
    boolean hasModifier(Nameable nameable);

}
