package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.Nameable;
import com.github.drsmugleaf.pokemon2.base.external.Smogon;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.type.TypeRegistry;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface IGeneration extends Nameable {

    Smogon getSmogon();
    Pokedex getPokedex();
    TypeRegistry getTypes();
    String getAbbreviation();
    ImmutableSet<Game> getCoreGames();
    ImmutableSet<Game> getSideGames();
    int getNewPokemons();
    int getTotalPokemons();

}
