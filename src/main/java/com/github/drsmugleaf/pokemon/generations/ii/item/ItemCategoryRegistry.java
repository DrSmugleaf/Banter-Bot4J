package com.github.drsmugleaf.pokemon.generations.ii.item;

import com.github.drsmugleaf.pokemon.base.registry.Registry;

import java.util.Collection;
import java.util.Map;

/**
 * Created by DrSmugleaf on 05/03/2020
 */
public class ItemCategoryRegistry extends Registry<IItemCategory> {

    public ItemCategoryRegistry(Map<String, IItemCategory> entries) {
        super(entries);
    }

    public ItemCategoryRegistry(Collection<IItemCategory> entries) {
        super(entries);
    }

    public ItemCategoryRegistry(IItemCategory[] entries) {
        super(entries);
    }

}
