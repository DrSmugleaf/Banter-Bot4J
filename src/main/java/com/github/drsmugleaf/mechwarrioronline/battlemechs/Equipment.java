package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import com.github.drsmugleaf.mechwarrioronline.equipment.Item;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 13/01/2019
 */
public class Equipment {

    @NotNull
    public final List<Item> ITEMS = new ArrayList<>();

    protected Equipment() {}

    public void addItem(@NotNull Item item) {
        ITEMS.add(item);
    }

}
