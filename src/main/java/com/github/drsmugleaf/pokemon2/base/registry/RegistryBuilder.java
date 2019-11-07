package com.github.drsmugleaf.pokemon2.base.registry;

import com.github.drsmugleaf.pokemon2.base.Nameable;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 08/07/2019
 */
public class RegistryBuilder<T extends Registry<T> & Nameable> extends TreeMap<String, T> {

    private final Function<Map<String, T>, T> CONSTRUCTOR;

    public RegistryBuilder(Function<Map<String, T>, T> constructor) {
        CONSTRUCTOR = constructor;
    }

    public T build() {
        return CONSTRUCTOR.apply(this);
    }

}
