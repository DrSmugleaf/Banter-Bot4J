package com.github.drsmugleaf.pokemon2.base.item;

import com.github.drsmugleaf.pokemon.item.ItemCategory;
import com.github.drsmugleaf.pokemon2.base.Nameable;
import com.google.common.collect.ImmutableSet;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface IItems<T extends IItems> extends Nameable {

    ImmutableSet<T> getItems();
    int getId();

    default String getHex() {
        return Integer.toHexString(getId());
    }

    ItemCategory getCategory();

    class Counter {

        public static int amount = 0;

    }

}
