package com.github.drsmugleaf.pokemon.generations.vi.item;

import com.github.drsmugleaf.pokemon.generations.ii.item.IItemCategory;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public enum ItemCategoriesVI implements IItemCategory {

    NONE("None"),
    BERRY("Berry"),
    ARCEUS_PLATE("Arceus Plate"),
    GENESECT_DRIVE("Genesect Drive"),
    MEGA_STONE("Mega Stone"),
    PRIMAL_ORB("Primal Orb");

    private final String NAME;

    ItemCategoriesVI(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
