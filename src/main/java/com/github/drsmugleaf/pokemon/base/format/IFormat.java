package com.github.drsmugleaf.pokemon.base.format;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IFormat extends Nameable {

    String getAbbreviation();
    ImmutableSet<String> getClauses();
    ImmutableSet<String> getBannedPokemon();
    ImmutableSet<String> getBannedMoves();
    ImmutableSet<String> getBannedTiers();
    ImmutableSet<String> getBannedItems();
    ImmutableSet<String> getBannedAbilities();
    ImmutableSet<String> getBannedCombinations();

}
