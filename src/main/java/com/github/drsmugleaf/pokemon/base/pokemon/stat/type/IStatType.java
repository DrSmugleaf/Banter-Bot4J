package com.github.drsmugleaf.pokemon.base.pokemon.stat.type;

import com.github.drsmugleaf.pokemon.base.registry.Columns;
import com.github.drsmugleaf.pokemon.base.registry.IColumns;
import com.github.drsmugleaf.pokemon.base.registry.IEntry;

import java.util.Map;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public interface IStatType extends IEntry {

    @Override
    default Map<String, String> export() {
        IColumns columns = new Columns();
        columns
                .put("name", getName())
                .put("abbreviation", getAbbreviation())
                .put("is_permanent", isPermanent());

        return columns.get();
    }

    String getAbbreviation();
    boolean isPermanent();

}
