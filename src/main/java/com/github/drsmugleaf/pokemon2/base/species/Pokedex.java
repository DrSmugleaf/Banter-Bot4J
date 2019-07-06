package com.github.drsmugleaf.pokemon2.base.species;

import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.registry.Registry;

import java.util.Map;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class Pokedex<T extends ISpecies> extends Registry<T> {

    public Pokedex(IGeneration generation, Function<SpeciesBuilder<T>, T> constructor) {
        super(getAll(generation, constructor));
    }

    private static <T extends ISpecies> Map<String, T> getAll(
            IGeneration generation,
            Function<SpeciesBuilder<T>, T> constructor
    ) {
        return generation.getSmogon().getSpecies(constructor);
    }

}
