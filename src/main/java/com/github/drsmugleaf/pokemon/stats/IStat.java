package com.github.drsmugleaf.pokemon.stats;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 14/09/2017.
 */
public interface IStat {

    @NotNull
    String getName();

    @NotNull
    String getAbbreviation();

    double calculate(@NotNull Pokemon pokemon);

    double calculateWithoutStages(@NotNull Pokemon pokemon);

}
