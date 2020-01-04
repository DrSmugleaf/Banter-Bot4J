package com.github.drsmugleaf.pokemon.generations.ii.generation;

import com.github.drsmugleaf.pokemon.base.generation.BaseGeneration;
import com.github.drsmugleaf.pokemon.generations.ii.item.ItemRegistry;

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
