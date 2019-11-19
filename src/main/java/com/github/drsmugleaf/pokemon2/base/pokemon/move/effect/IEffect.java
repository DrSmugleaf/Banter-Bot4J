package com.github.drsmugleaf.pokemon2.base.pokemon.move.effect;

import com.github.drsmugleaf.pokemon2.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveInformation;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveReport;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IEffect<T extends IBattlePokemon> {

    int getID();
    int use(IMoveInformation<T> move, T user, T target);
    void effect(IMoveInformation<T> move, T user, T target, IMoveReport<T> report);

}
