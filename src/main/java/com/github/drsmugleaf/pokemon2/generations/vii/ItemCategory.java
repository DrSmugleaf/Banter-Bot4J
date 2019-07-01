package com.github.drsmugleaf.pokemon2.generations.vii;

import com.github.drsmugleaf.pokemon2.base.item.IItemCategory;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public enum ItemCategory implements IItemCategory {

    SILVALLY_MEMORY("Silvally Memory"),
    Z_CRYSTAL("Z-Crystal");

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
