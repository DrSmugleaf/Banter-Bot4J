package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.external.Smogon;
import com.github.drsmugleaf.pokemon2.base.format.FormatRegistry;
import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.StatRegistry;
import com.github.drsmugleaf.pokemon2.base.pokemon.type.TypeRegistry;

import java.util.Set;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface IGeneration extends Nameable {

    int getID();
    @Nullable IGeneration getPrevious();
    Set<IGeneration> getAllPrevious();
    @Nullable IGeneration getNext();
    Set<IGeneration> getAllNext();
    Smogon getSmogon();
    Pokedex<? extends ISpecies<?>> getPokedex();
    TypeRegistry getTypes();
    FormatRegistry getFormats();
    StatRegistry getStats();
    String getAbbreviation();
    GameRegistry getGames();
    int getNewPokemons();
    int getTotalPokemons();

}
