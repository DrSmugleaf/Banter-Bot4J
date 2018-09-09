package com.github.drsmugleaf.pokemon.item;

import javax.annotation.Nonnull;

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

    @Nonnull
    public final String NAME;

    ItemCategory(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
