package com.github.drsmugleaf.pokemon.generations.viii.item;

import com.github.drsmugleaf.pokemon.generations.ii.item.IItemCategory;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 05/03/2020
 */
public enum ItemCategoriesVIII implements IItemCategory {

    NONE("None"),
    BERRY("Berry"),
    ARCEUS_PLATE("Arceus Plate"),
    SILVALLY_MEMORY("Silvally Memory");

    private final String NAME;

    ItemCategoriesVIII(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
