package com.github.drsmugleaf.pokemon2.base.generation;

import com.github.drsmugleaf.pokemon.battle.Game;
import com.github.drsmugleaf.pokemon2.base.Nameable;
import com.github.drsmugleaf.pokemon2.base.type.TypeRegistry;
import com.github.drsmugleaf.pokemon2.generations.i.GenerationI;
import com.github.drsmugleaf.pokemon2.generations.ii.GenerationII;
import com.github.drsmugleaf.pokemon2.generations.iii.GenerationIII;
import com.github.drsmugleaf.pokemon2.generations.iv.GenerationIV;
import com.github.drsmugleaf.pokemon2.generations.v.GenerationV;
import com.github.drsmugleaf.pokemon2.generations.vi.GenerationVI;
import com.github.drsmugleaf.pokemon2.generations.vii.GenerationVII;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface IGeneration extends Nameable {

    IGeneration[] GENERATIONS = new IGeneration[]{
            GenerationI.get(),
            GenerationII.get(),
            GenerationIII.get(),
            GenerationIV.get(),
            GenerationV.get(),
            GenerationVI.get(),
            GenerationVII.get()
    };

    static IGeneration from(String abbreviation) {
        for (IGeneration generation : GENERATIONS) {
            if (generation.getAbbreviation().equalsIgnoreCase(abbreviation)) {
                return generation;
            }
        }

        throw new IllegalArgumentException("No generation found with abbreviation " + abbreviation);
    }

    String getAbbreviation();
    ImmutableSet<Game> getCoreGames();
    ImmutableSet<Game> getSideGames();
    int getNewPokemons();
    int getTotalPokemons();
    TypeRegistry getTypes();

}
