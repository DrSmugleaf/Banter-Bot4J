package com.github.drsmugleaf.pokemon.generations.ii.item;

import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public enum ItemCategoriesII implements IItemCategory {

    NONE("None"),
    BERRY("Berry");

    private final String NAME;

    ItemCategoriesII(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
