package com.github.drsmugleaf.pokemon2.generations.iv.item;

import com.github.drsmugleaf.pokemon2.generations.ii.item.IItemCategory;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public enum ItemCategoriesIV implements IItemCategory {

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
