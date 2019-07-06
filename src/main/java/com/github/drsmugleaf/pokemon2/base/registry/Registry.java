package com.github.drsmugleaf.pokemon2.base.registry;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public abstract class Registry<T> {

    private final ImmutableMap<String, T> MAP;

    public Registry(Map<String, T> map) {
        MAP = ImmutableMap.copyOf(map);
    }

    public ImmutableMap<String, T> get() {
        return MAP;
    }

}
