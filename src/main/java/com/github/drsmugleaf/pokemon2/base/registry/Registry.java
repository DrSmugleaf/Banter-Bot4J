package com.github.drsmugleaf.pokemon2.base.registry;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public abstract class Registry<T extends Nameable> {

    private final ImmutableMap<String, T> MAP;

    public Registry(Map<String, T> map) {
        MAP = ImmutableMap.copyOf(map);
    }

    public Registry(Set<T> set) {
        MAP = set.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toMap(Nameable::getName, Function.identity()),
                        ImmutableMap::copyOf
                )
        );
    }

    public ImmutableMap<String, T> get() {
        return MAP;
    }

    public T get(String name) {
        return MAP.get(name);
    }

}
