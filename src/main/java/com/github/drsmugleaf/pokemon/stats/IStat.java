package com.github.drsmugleaf.pokemon.stats;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 14/09/2017.
 */
public interface IStat {

    @Nonnull
    String getName();

    @Nonnull
    String getAbbreviation();

    double calculate(@Nonnull Pokemon pokemon);

    double calculateWithoutStages(@Nonnull Pokemon pokemon);

}
