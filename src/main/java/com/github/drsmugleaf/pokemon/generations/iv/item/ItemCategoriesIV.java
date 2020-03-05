package com.github.drsmugleaf.pokemon.generations.iv.item;

import com.github.drsmugleaf.pokemon.generations.ii.item.IItemCategory;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public enum ItemCategoriesIV implements IItemCategory {

    NONE("None"),
    BERRY("Berry"),
    ARCEUS_PLATE("Arceus Plate");

    private final String NAME;

    ItemCategoriesIV(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
