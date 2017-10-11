package com.github.drsmugbrain.pokemon.stats;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 14/09/2017.
 */
public interface IStat {

    @Nonnull
    String getName();

    @Nonnull
    String getAbbreviation();

}
