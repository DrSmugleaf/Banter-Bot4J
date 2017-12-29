package com.github.drsmugleaf.pokemon.status;

import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 11/10/2017.
 */
public interface IStatus {

    void apply(@Nonnull Pokemon pokemon, @Nonnull Action action);

    void remove(@Nonnull Pokemon pokemon);

}
