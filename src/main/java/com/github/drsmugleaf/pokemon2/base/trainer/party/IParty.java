package com.github.drsmugleaf.pokemon2.base.trainer.party;

import com.github.drsmugleaf.pokemon2.base.battle.IBattle;
import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;

import java.util.Set;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface IParty<T extends IPokemon<T>> {

    Set<IPokemon<T>> getPokemon();
    IPokemon<T> getPokemon(int i);
    boolean add(IPokemon<T> pokemon, int slot);
    boolean add(IPokemon<T> pokemon);
    boolean remove(int slot);
    boolean sendOut(IBattle<T> battle, int slot);
    boolean sendBack(IBattle<T> battle, int slot);

}
