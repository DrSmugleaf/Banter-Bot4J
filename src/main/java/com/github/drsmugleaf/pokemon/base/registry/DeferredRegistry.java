package com.github.drsmugleaf.pokemon.base.registry;

import com.github.drsmugleaf.Nullable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 07/01/2020
 */
public class DeferredRegistry<T extends IEntry> implements IDeferredRegistry<T> {

    private final ImmutableSet<String> SET;
    private final Function<String, T> LOOKUP;
    private final Map<String, T> MAP = new HashMap<>();
    @Nullable
    private Registry<T> fullRegistry = null;

    public DeferredRegistry(Function<String, T> lookup) {
        SET = ImmutableSet.of();
        LOOKUP = lookup;
    }

    public DeferredRegistry(Collection<String> collection, Function<String, T> lookup) {
        SET = ImmutableSet.copyOf(collection);
        LOOKUP = lookup;
    }

    protected Registry<T> getFullRegistry() {
        if (fullRegistry == null) {
            for (String name : SET) {
                if (MAP.containsKey(name)) {
                    continue;
                }

                MAP.put(name, LOOKUP.apply(name));
            }

            fullRegistry = new Registry<>(MAP);
        }

        return fullRegistry;
    }

    @Override
    public ImmutableSet<String> getRaw() {
        return SET;
    }

    @Override
    public ImmutableMap<String, T> get() {
        return getFullRegistry().get();
    }

    @Override
    public T get(String name) {
        T entry;
        if (!MAP.containsKey(name)) {
            entry = LOOKUP.apply(name);
            MAP.put(name, entry);
        } else {
            entry = MAP.get(name);
        }

        return entry;
    }

    @Override
    public String joining(CharSequence delimiter) {
        return getFullRegistry().joining(delimiter);
    }

    @Override
    public String joining() {
        return getFullRegistry().joining();
    }

    @Override
    public List<Map<String, String>> export() {
        return getFullRegistry().export();
    }

}
