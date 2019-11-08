package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.external.Smogon;
import com.github.drsmugleaf.pokemon2.base.game.GameRegistry;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.type.TypeRegistry;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface IGeneration extends Nameable {

    Smogon getSmogon();
    Pokedex<? extends ISpecies<?>> getPokedex();
    TypeRegistry getTypes();
    String getAbbreviation();
    GameRegistry getGames();
    int getNewPokemons();
    int getTotalPokemons();

}
