package com.github.drsmugleaf.pokemon2.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.battle.IBattle;
import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.IAllowedModifier;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.INamedAllowedModifier;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.INonVolatileStatus;
import com.google.common.collect.ImmutableMultimap;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public interface IBattlePokemon<T extends IBattlePokemon<?>> extends IPokemon<T> {

    int getID();
    IBattle<T> getBattle();
    @Nullable INonVolatileStatus<? super T> getStatus();
    void setStatus(@Nullable INonVolatileStatus<? super T> status);
    IPokemonState getState();
    void setState(IPokemonState state);
    ImmutableMultimap<IPokemonState, INamedAllowedModifier> getModifiers();
    boolean hasModifier(String name);
    @Nullable
    INamedAllowedModifier getModifier(String name);
    void addModifier(IPokemonState state, Nameable nameable, IAllowedModifier modifier);
    void addModifierUnique(IPokemonState state, Nameable nameable, IAllowedModifier modifier);

}
