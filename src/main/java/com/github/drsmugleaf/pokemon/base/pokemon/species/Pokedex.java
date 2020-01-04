package com.github.drsmugleaf.pokemon.base.pokemon.species;

import com.github.drsmugleaf.pokemon.base.registry.Registry;

import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public abstract class Pokedex<T extends ISpecies> extends Registry<T> {

    public Pokedex(Map<String, T> map) {
        super(map);
    }

}
