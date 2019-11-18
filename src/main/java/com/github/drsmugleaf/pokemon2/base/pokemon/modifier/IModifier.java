package com.github.drsmugleaf.pokemon2.base.pokemon.modifier;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.IPokemonState;

/**
 * Created by DrSmugleaf on 18/11/2019
 */
public interface IModifier extends Nameable {

    IPokemonState forState();
    boolean execute();

}
