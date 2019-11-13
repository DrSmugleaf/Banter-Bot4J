package com.github.drsmugleaf.pokemon2.generations.i.pokemon.move;

import com.github.drsmugleaf.pokemon2.base.pokemon.move.IDamageCategory;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum DamageCategoriesI implements IDamageCategory {

    PHYSICAL("Physical"),
    SPECIAL("Special"),
    STATUS("Status");

    private final String NAME;

    DamageCategoriesI(String name) {
        NAME = name;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
