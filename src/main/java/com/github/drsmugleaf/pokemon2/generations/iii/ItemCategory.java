package com.github.drsmugleaf.pokemon2.generations.iii;

import com.github.drsmugleaf.pokemon2.base.item.IItemCategory;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public enum ItemCategory implements IItemCategory {

    ;

    private final String NAME;

    ItemCategory(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
