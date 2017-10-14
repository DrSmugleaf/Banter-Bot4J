package com.github.drsmugbrain.pokemon.item;

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

    @Nullable
    public Items get() {
        return item;
    }

    public void set(@Nullable Items item) {
        this.item = item;
    }

}
