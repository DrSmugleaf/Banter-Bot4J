package com.github.drsmugleaf.pokemon2.base.pokemon.move.effect;

import com.github.drsmugleaf.pokemon2.base.battle.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveInformation;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IEffect<T extends IBattlePokemon> {

    int getID();
    void use(IMoveInformation<T> move, T target, T user);

}
