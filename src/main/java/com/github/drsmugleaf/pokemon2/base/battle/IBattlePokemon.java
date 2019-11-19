package com.github.drsmugleaf.pokemon2.base.battle;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.IModifier;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.INamedModifier;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.IStatus;
import com.google.common.collect.ImmutableMultimap;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public interface IBattlePokemon<T extends IBattlePokemon<T>> extends IPokemon<T> {

    IBattle<T> getBattle();
    @Nullable IStatus<? super T> getStatus();
    void setStatus(@Nullable IStatus<? super T> status);
    IPokemonState getState();
    void setState(IPokemonState state);
    ImmutableMultimap<IPokemonState, INamedModifier> getModifiers();
    boolean hasModifier(String name);
    @Nullable INamedModifier getModifier(String name);
    void addModifier(IPokemonState state, Nameable nameable, IModifier modifier);
    void addModifierUnique(IPokemonState state, Nameable nameable, IModifier modifier);

}
