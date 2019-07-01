package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.Nameable;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface Generation extends Nameable {

    String getAbbreviation();
    ImmutableSet<Game> getCoreGames();
    ImmutableSet<Game> getSideGames();
    int getNewPokemons();
    int getTotalPokemons();

}
