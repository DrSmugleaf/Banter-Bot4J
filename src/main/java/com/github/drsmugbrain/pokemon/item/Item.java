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

    public Item(@Nonnull Items item) {
        this.item = item;
    }

    @Nonnull
    public String getName() {
        return item.getName();
    }

    @Nonnull
    public ItemCategory getCategory() {
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
