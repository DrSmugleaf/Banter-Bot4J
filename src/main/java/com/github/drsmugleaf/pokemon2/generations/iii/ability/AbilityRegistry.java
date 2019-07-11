package com.github.drsmugleaf.pokemon2.generations.iii.ability;

import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.registry.Registry;

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
