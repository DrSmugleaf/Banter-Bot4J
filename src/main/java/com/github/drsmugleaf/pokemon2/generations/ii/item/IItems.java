package com.github.drsmugleaf.pokemon2.generations.ii.item;

import com.github.drsmugleaf.pokemon.item.ItemCategory;
import com.github.drsmugleaf.pokemon2.base.Nameable;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface IItems extends Nameable {

    int getId();

    default String getHex() {
        return Integer.toHexString(getId());
    }

    ItemCategory getCategory();

}
