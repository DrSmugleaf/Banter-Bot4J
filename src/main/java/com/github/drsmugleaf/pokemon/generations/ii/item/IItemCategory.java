package com.github.drsmugleaf.pokemon.generations.ii.item;

import com.github.drsmugleaf.pokemon.base.registry.Columns;
import com.github.drsmugleaf.pokemon.base.registry.IColumns;
import com.github.drsmugleaf.pokemon.base.registry.IEntry;

import java.util.Map;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface IItemCategory extends IEntry {

    @Override
    default Map<String, String> export() {
        IColumns columns = new Columns();
        columns
                .put("name", getName());

        return columns.get();
    }

}
