package com.github.drsmugleaf.pokemon.base.trainer.party;

import com.github.drsmugleaf.pokemon.base.battle.IBattle;
import com.github.drsmugleaf.pokemon.base.pokemon.IBattlePokemon;

import java.util.Set;

/**
 * Created by DrSmugleaf on 14/11/2019
 */
public interface IParty<T extends IBattlePokemon<T>> {

    Set<T> getPokemon();
    T getPokemon(int i);
    boolean add(T pokemon, int slot);
    boolean add(T pokemon);
    boolean remove(int slot);
    boolean sendOut(IBattle<T> battle, int slot);
    boolean sendBack(IBattle<T> battle, int slot);

}
