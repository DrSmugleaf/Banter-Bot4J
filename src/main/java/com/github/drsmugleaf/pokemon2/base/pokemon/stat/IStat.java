package com.github.drsmugleaf.pokemon2.base.pokemon.stat;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.IPokemon;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public interface IStat extends Nameable {

    String getAbbreviation();
    boolean isPermanent();
    int calculate(IPokemon<?> pokemon);

}
