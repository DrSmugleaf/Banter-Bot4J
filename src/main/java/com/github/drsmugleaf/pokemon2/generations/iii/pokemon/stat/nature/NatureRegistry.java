package com.github.drsmugleaf.pokemon2.generations.iii.pokemon.stat.nature;

import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.registry.Registry;

import java.util.Map;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class NatureRegistry extends Registry<INature> {

    public NatureRegistry(IGeneration generation) {
        super(getAll(generation));
    }

    private static Map<String, INature> getAll(IGeneration generation) {
        return generation.getSmogon().getNatures();
    }

}
