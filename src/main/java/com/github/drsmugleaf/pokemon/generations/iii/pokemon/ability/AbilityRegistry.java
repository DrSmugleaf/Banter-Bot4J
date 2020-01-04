package com.github.drsmugleaf.pokemon.generations.iii.pokemon.ability;

import com.github.drsmugleaf.pokemon.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon.base.registry.Registry;

import java.util.Map;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class AbilityRegistry extends Registry<IAbility> {

    public AbilityRegistry(IGeneration generation) {
        super(getAll(generation));
    }

    private static Map<String, IAbility> getAll(IGeneration generation) {
        return generation.getSmogon().getAbilities();
    }

}
