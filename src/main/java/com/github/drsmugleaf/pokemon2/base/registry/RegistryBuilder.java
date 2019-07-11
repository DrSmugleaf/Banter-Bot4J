package com.github.drsmugleaf.pokemon2.base.registry;

import com.github.drsmugleaf.pokemon2.base.builder.IBuilder;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 08/07/2019
 */
public class RegistryBuilder<T extends Registry<T>> extends TreeMap<String, T> implements IBuilder<T> {

    private final Function<Map<String, T>, T> CONSTRUCTOR;

    public RegistryBuilder(Function<Map<String, T>, T> constructor) {
        CONSTRUCTOR = constructor;
    }

    @Override
    public T build() {
        return CONSTRUCTOR.apply(this);
    }

}
