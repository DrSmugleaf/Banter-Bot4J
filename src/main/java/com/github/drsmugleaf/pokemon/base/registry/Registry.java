package com.github.drsmugleaf.pokemon.base.registry;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.google.common.collect.ImmutableMap;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class Registry<T extends IEntry> implements IRegistry<T> {

    private final ImmutableMap<String, T> MAP;

    public Registry() {
        MAP = ImmutableMap.of();
    }

    public Registry(Map<String, T> map) {
        MAP = ImmutableMap.copyOf(map);
    }

    public Registry(Collection<T> collection) {
        this(collection.stream().collect(Collectors.toMap(Nameable::getName, Function.identity())));
    }

    public Registry(T[] elements) {
        this(Arrays.stream(elements).collect(Collectors.toMap(Nameable::getName, Function.identity())));
    }

    public static <T extends IEntry> Registry<T> from() {
        return new Registry<>();
    }

    public static <T extends IEntry> Registry<T> from(Map<String, T> map) {
        return new Registry<>(map);
    }

    public static <T extends IEntry> Registry<T> from(Collection<T> set) {
        return new Registry<>(set);
    }

    public static <T extends IEntry> Registry<T> from(T[] elements) {
        return new Registry<>(elements);
    }

    @Override
    public ImmutableMap<String, T> get() {
        return MAP;
    }

    @Override
    public T get(String name) {
        return MAP.get(name);
    }

    @Override
    public String joining(CharSequence delimiter) {
        return get().values().stream().map(Nameable::getName).collect(Collectors.joining(delimiter));
    }

    @Override
    public String joining() {
        return joining(",");
    }

    @Override
    public List<Map<String, String>> export() {
        List<Map<String, String>> lines = new ArrayList<>();
        for (T value : get().values()) {
            lines.add(value.export());
        }

        return lines;
    }

}
