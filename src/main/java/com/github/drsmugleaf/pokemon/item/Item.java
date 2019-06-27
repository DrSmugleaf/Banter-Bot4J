package com.github.drsmugleaf.pokemon.item;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 15/10/2017.
 */
public class Item {

    private Items item;

    public Item(Items item) {
        this.item = item;
    }

    public String getName() {
        return item.NAME;
    }

    public ItemCategory getCategory() {
        return item.CATEGORY;
    }

    @Contract(pure = true)
    @Nullable
    public Items get() {
        return item;
    }

    public boolean is() {
        return item != Items.NONE;
    }

    public boolean is(Items item) {
        return this.item == item;
    }

    public boolean is(ItemCategory category) {
        return item.CATEGORY == category;
    }

    public Items remove() {
        return set(Items.NONE);
    }

    public Items set(Items item) {
        Items oldItem = this.item;
        this.item = item;
        return oldItem;
    }

    public Items steal(Pokemon pokemon) {
        Items stolenItem = pokemon.ITEM.remove();

        if (stolenItem != Items.NONE) {
            set(stolenItem);
        }

        return stolenItem;
    }

    public void tryUse(Pokemon pokemon) {
        if (item != Items.NONE) {
            item.use(pokemon, pokemon.getBattle());
        }
    }

}
