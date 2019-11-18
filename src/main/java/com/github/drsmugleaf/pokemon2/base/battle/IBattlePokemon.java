package com.github.drsmugleaf.pokemon2.base.battle;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;

/**
 * Created by DrSmugleaf on 17/11/2019
 */
public interface IBattlePokemon<T extends IBattlePokemon<T>> extends IPokemon<T> {

    IBattle<T> getBattle();


}
