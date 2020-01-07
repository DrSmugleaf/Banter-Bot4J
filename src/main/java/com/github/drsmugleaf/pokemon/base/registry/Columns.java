package com.github.drsmugleaf.pokemon.base.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 04/01/2020
 */
public class Columns implements IColumns {

    private final Map<String, String> columns = new HashMap<>();

    public Columns() {}

    @Override
    public Map<String, String> get() {
        return new HashMap<>(columns);
    }

    @Override
    public String get(String column) {
        return columns.get(column);
    }

    @Override
    public IColumns put(String column, Object object) {
        columns.put(column, object.toString());
        return this;
    }

}
