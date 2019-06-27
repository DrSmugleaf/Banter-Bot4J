package com.github.drsmugleaf.pokemon.status;

import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 11/10/2017.
 */
public interface IStatus {

    void apply(Pokemon pokemon, Action action);

    void remove(Pokemon pokemon);

}
