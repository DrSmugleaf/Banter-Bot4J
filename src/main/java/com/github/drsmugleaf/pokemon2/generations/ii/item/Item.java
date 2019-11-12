package com.github.drsmugleaf.pokemon2.generations.ii.item;

import com.github.drsmugleaf.pokemon.item.ItemCategory;

/**
 * Created by DrSmugleaf on 11/11/2019
 */
public class Item implements IItem {

    private final ItemCategory CATEGORY;
    private final String NAME;

    public Item(ItemCategory category, String name) {
        CATEGORY = category;
        NAME = name;
    }

    public Item(String name) {
        this(ItemCategory.DEFAULT, name);
    }

    @Override
    public ItemCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
