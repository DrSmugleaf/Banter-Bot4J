package com.github.drsmugleaf.pokemon.item;

import com.github.drsmugleaf.pokemon.pokemon.Pokemon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by DrSmugleaf on 15/10/2017.
 */
public class Item {

    @NotNull
    private Items item;

    public Item(@NotNull Items item) {
        this.item = item;
    }

    @NotNull
    public String getName() {
        return item.NAME;
    }

    @NotNull
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

    public boolean is(@NotNull Items item) {
        return this.item == item;
    }

    public boolean is(@NotNull ItemCategory category) {
        return item.CATEGORY == category;
    }

    @NotNull
    public Items remove() {
        return set(Items.NONE);
    }

    @NotNull
    public Items set(@NotNull Items item) {
        Items oldItem = this.item;
        this.item = item;
        return oldItem;
    }

    @NotNull
    public Items steal(@NotNull Pokemon pokemon) {
        Items stolenItem = pokemon.ITEM.remove();

        if (stolenItem != Items.NONE) {
            set(stolenItem);
        }

        return stolenItem;
    }

    public void tryUse(@NotNull Pokemon pokemon) {
        if (item != Items.NONE) {
            item.use(pokemon, pokemon.getBattle());
        }
    }

}
