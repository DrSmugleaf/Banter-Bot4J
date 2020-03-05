package com.github.drsmugleaf.pokemon.generations.v.item;

import com.github.drsmugleaf.pokemon.generations.ii.item.IItemCategory;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public enum ItemCategoriesV implements IItemCategory {

    NONE("None"),
    BERRY("Berry"),
    ARCEUS_PLATE("Arceus Plate"),
    GENESECT_DRIVE("Genesect Drive");

    private final String NAME;

    ItemCategoriesV(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
