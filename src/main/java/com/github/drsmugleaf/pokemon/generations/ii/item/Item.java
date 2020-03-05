package com.github.drsmugleaf.pokemon.generations.ii.item;

/**
 * Created by DrSmugleaf on 11/11/2019
 */
public class Item implements IItem {

    private final IItemCategory CATEGORY;
    private final String NAME;

    public Item(IItemCategory category, String name) {
        CATEGORY = category;
        NAME = name;
    }

    public Item(String name) {
        this(ItemCategoriesII.NONE, name);
    }

    @Override
    public IItemCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
