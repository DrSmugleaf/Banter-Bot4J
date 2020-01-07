package com.github.drsmugleaf.pokemon.base.format;

import com.github.drsmugleaf.pokemon.base.registry.Columns;
import com.github.drsmugleaf.pokemon.base.registry.IColumns;
import com.github.drsmugleaf.pokemon.base.registry.IEntry;
import com.google.common.collect.ImmutableSet;

import java.util.Map;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IFormat extends IEntry {

    @Override
    default Map<String, String> export() {
        IColumns columns = new Columns();
        columns
                .put("name", getName())
                .put("abbreviation", getAbbreviation());

        return columns.get();
    }

    String getAbbreviation();
    ImmutableSet<String> getClauses();
    ImmutableSet<String> getBannedPokemon();
    ImmutableSet<String> getBannedMoves();
    ImmutableSet<String> getBannedTiers();
    ImmutableSet<String> getBannedItems();
    ImmutableSet<String> getBannedAbilities();
    ImmutableSet<String> getBannedCombinations();

}
