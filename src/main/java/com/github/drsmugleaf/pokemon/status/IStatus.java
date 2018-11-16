package com.github.drsmugleaf.pokemon.status;

import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 11/10/2017.
 */
public interface IStatus {

    void apply(@NotNull Pokemon pokemon, @NotNull Action action);

    void remove(@NotNull Pokemon pokemon);

}
