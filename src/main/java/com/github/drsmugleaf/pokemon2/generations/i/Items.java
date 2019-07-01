package com.github.drsmugleaf.pokemon2.generations.i;

import com.github.drsmugleaf.pokemon.item.ItemCategory;
import com.github.drsmugleaf.pokemon2.base.item.IItems;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public enum Items implements IItems {

    NONE("None");

    private final int ID;
    private final String NAME;

    Items(int id, String name) {
        ID = id;
        NAME = name;
    }

    Items(String name) {
        this(Counter.amount++, name);
    }

    @Override
    public ImmutableSet<Items> getItems() {
        return ImmutableSet.copyOf(values());
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public ItemCategory getCategory() {
        return null;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
