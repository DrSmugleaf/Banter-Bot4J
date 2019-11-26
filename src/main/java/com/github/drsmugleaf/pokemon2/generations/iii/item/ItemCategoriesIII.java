package com.github.drsmugleaf.pokemon2.generations.iii.item;

import com.github.drsmugleaf.pokemon2.generations.ii.item.IItemCategory;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public enum ItemCategoriesIII implements IItemCategory {

    ;

    private final String NAME;

    ItemCategoriesIII(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
