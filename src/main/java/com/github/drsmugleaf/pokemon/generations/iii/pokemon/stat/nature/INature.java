package com.github.drsmugleaf.pokemon.generations.iii.pokemon.stat.nature;

import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon.base.registry.Columns;
import com.github.drsmugleaf.pokemon.base.registry.IColumns;
import com.github.drsmugleaf.pokemon.base.registry.IEntry;

import java.util.Map;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public interface INature extends IEntry {

    @Override
    default Map<String, String> export() {
        IColumns columns = new Columns();
        columns.put("name", getName());

        return columns.get();
    }

    double getStatMultiplier(IStatType stat);

}
