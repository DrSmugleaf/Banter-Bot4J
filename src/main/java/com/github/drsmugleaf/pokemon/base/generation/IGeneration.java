package com.github.drsmugleaf.pokemon.base.generation;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.base.external.Smogon;
import com.github.drsmugleaf.pokemon.base.format.FormatRegistry;
import com.github.drsmugleaf.pokemon.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.StatTypeRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.type.TypeRegistry;

import java.util.SortedSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface IGeneration extends Nameable, Comparable<IGeneration> {

    @Override
    default int compareTo(IGeneration o) {
        return Integer.compare(getID(), o.getID());
    }
    int getID();
    @Nullable
    IGeneration getPrevious();
    SortedSet<IGeneration> getAllPrevious();
    @Nullable
    IGeneration getNext();
    SortedSet<IGeneration> getAllNext();
    Smogon getSmogon();
    Pokedex<? extends ISpecies> getPokedex();
    TypeRegistry getTypes();
    FormatRegistry getFormats();
    StatTypeRegistry getStats();
    String getAbbreviation();
    GameRegistry getGames();
    int getNewPokemon();
    int getTotalPokemon();

}
