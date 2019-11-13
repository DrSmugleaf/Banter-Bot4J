package com.github.drsmugleaf.pokemon2.base.pokemon.move.effect;

import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IMoveInformation;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IEffect {

    int getID();
    void use(IMoveInformation move, IPokemon target, IPokemon user);

}
