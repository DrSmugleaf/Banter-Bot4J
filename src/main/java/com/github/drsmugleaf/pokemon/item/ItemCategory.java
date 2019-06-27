package com.github.drsmugleaf.pokemon.item;

import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 18/09/2017.
 */
public enum ItemCategory {

    DEFAULT("Default"),
    BERRY("Berry"),
    MEGA_STONE("Mega Stone"),
    PRIMAL_ORB("Primal Orb"),
    Z_CRYSTAL("Z-Crystal"),
    ARCEUS_PLATE("Arceus Plate"),
    GENESECT_DRIVE("Genesect Drive"),
    SILVALLY_MEMORY("Silvally Memory");

    public final String NAME;

    ItemCategory(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

}
