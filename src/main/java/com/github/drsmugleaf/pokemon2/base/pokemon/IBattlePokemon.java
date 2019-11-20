package com.github.drsmugleaf.pokemon2.base.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.battle.IBattle;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.IModifiers;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.INonVolatileStatus;

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
    IModifiers getModifiers();

}
