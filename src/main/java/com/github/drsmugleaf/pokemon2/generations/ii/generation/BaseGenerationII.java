package com.github.drsmugleaf.pokemon2.generations.ii.generation;

import com.github.drsmugleaf.pokemon2.base.generation.BaseGeneration;
import com.github.drsmugleaf.pokemon2.generations.ii.item.ItemRegistry;

/**
 * Created by DrSmugleaf on 11/11/2019
 */
public abstract class BaseGenerationII extends BaseGeneration implements IGenerationII {

    private final ItemRegistry ITEMS = new ItemRegistry(this);

    protected BaseGenerationII() {
        super();
    }

    @Override
    public ItemRegistry getItems() {
        return ITEMS;
    }

}
