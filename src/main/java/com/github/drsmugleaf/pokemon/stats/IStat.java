package com.github.drsmugleaf.pokemon.stats;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

/**
 * Created by DrSmugleaf on 14/09/2017.
 */
public interface IStat {

    String getName();

    String getAbbreviation();

    double calculate(Pokemon pokemon);

    double calculateWithoutStages(Pokemon pokemon);

}
