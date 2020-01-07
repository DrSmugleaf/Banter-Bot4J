package com.github.drsmugleaf.pokemon.base.game;

import com.github.drsmugleaf.pokemon.base.registry.Columns;
import com.github.drsmugleaf.pokemon.base.registry.IColumns;
import com.github.drsmugleaf.pokemon.base.registry.IRegistrable;

import java.util.Map;

/**
 * Created by DrSmugleaf on 07/11/2019
 */
public interface IGame extends IRegistrable {

    @Override
    default Map<String, String> export() {
        IColumns columns = new Columns();
        columns
                .put("name", getName())
                .put("is_core", isCore());

        return columns.get();
    }

    boolean isCore();

}
