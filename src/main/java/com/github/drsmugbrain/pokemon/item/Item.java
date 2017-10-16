package com.github.drsmugbrain.pokemon.item;

import com.github.drsmugbrain.pokemon.pokemon.Pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 15/10/2017.
 */
public class Item {

    @Nullable
    private Items item;

    public Item(@Nullable Items item) {
        this.item = item;
    }

    @Nullable
    public String getName() {
        if (item == null) {
            return null;
        }

        return item.getName();
    }

    @Nullable
    public ItemCategory getCategory() {
        if (item == null) {
            return null;
        }

        return item.getCategory();
    }

    @Nullable
    public Items get() {
        return item;
    }

    public boolean is() {
        return item != null;
    }

    public boolean is(@Nullable Items item) {
        return this.item == item;
    }

    public boolean is(@Nullable ItemCategory category) {
        if (item == null && category == null) {
            return true;
        }

        return item != null && item.getCategory() == category;
    }

    public Items remove() {
        Items item = this.item;
        this.item = null;
        return item;
    }

    public void set(@Nonnull Items item) {
        this.item = item;
    }

    public void steal(@Nonnull Pokemon pokemon) {
        Items item = pokemon.ITEM.remove();
        if (item != null) {
            set(item);
        }
    }

    public void use(@Nonnull Pokemon pokemon) {
        if (item != null) {
            item.use(pokemon, pokemon.getBattle());
        }
    }

}
