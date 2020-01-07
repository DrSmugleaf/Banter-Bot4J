package com.github.drsmugleaf.pokemon.base.pokemon.species;

import com.github.drsmugleaf.pokemon.base.registry.Registry;

import java.util.Collection;
import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public abstract class SpeciesRegistry<T extends ISpecies> extends Registry<T> {

    public SpeciesRegistry(Map<String, T> map) {
        super(map);
    }

    public SpeciesRegistry(Collection<T> set) {
        super(set);
    }

}
