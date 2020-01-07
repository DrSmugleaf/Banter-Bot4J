package com.github.drsmugleaf.pokemon.base.registry;

import java.util.Map;

/**
 * Created by DrSmugleaf on 04/01/2020
 */
public interface IColumns {

    Map<String, String> get();
    String get(String column);
    IColumns put(String column, Object object);

}
