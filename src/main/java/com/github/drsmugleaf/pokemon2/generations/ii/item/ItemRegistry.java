package com.github.drsmugleaf.pokemon2.generations.ii.item;

import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.registry.Registry;

import java.util.Map;

/**
 * Created by DrSmugleaf on 11/11/2019
 */
public class ItemRegistry extends Registry<IItem> {

    public ItemRegistry(IGeneration generation) {
        super(getAll(generation));
    }

    private static Map<String, IItem> getAll(IGeneration generation) {
        return generation.getSmogon().getItems();
    }

}
