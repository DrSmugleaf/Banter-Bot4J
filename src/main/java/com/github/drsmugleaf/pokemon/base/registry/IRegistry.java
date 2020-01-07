package com.github.drsmugleaf.pokemon.base.registry;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IRegistry<T extends IEntry> {

    ImmutableMap<String, T> get();
    T get(String name);
    String joining(CharSequence delimiter);
    String joining();
    List<Map<String, String>> export();

}
