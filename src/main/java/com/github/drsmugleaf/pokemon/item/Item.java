package com.github.drsmugleaf.pokemon.item;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 15/10/2017.
 */
public class Item {

    @Nonnull
    private Items item;

    public Item(@Nonnull Items item) {
        this.item = item;
    }

    @Nonnull
    public String getName() {
        return item.NAME;
    }

    @Nonnull
    public ItemCategory getCategory() {
        return item.CATEGORY;
    }

    @Nullable
    public Items get() {
        return item;
    }

    public boolean is() {
        return item != Items.NONE;
    }

    public boolean is(@Nonnull Items item) {
        return this.item == item;
    }

    public boolean is(@Nonnull ItemCategory category) {
        return item.CATEGORY == category;
    }

    @Nonnull
    public Items remove() {
        return set(Items.NONE);
    }

    @Nonnull
    public Items set(@Nonnull Items item) {
        Items oldItem = this.item;
        this.item = item;
        return oldItem;
    }

    @Nonnull
    public Items steal(@Nonnull Pokemon pokemon) {
        Items stolenItem = pokemon.ITEM.remove();

        if (stolenItem != Items.NONE) {
            set(stolenItem);
        }

        return stolenItem;
    }

    public void tryUse(@Nonnull Pokemon pokemon) {
        if (item != Items.NONE) {
            item.use(pokemon, pokemon.getBattle());
        }
    }

}
