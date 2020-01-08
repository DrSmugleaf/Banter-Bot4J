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

    public Registry(Map<String, T> entries) {
        MAP = ImmutableMap.copyOf(entries);
    }

    public Registry(Collection<T> entries) {
        this(entries.stream().collect(Collectors.toMap(Nameable::getName, Function.identity())));
    }

    public Registry(T[] entries) {
        this(Arrays.stream(entries).collect(Collectors.toMap(Nameable::getName, Function.identity())));
    }

    public static <T extends IEntry> Registry<T> from() {
        return new Registry<>();
    }

    public static <T extends IEntry> Registry<T> from(Map<String, T> entries) {
        return new Registry<>(entries);
    }

    public static <T extends IEntry> Registry<T> from(Collection<T> entries) {
        return new Registry<>(entries);
    }

    public static <T extends IEntry> Registry<T> from(T[] entries) {
        return new Registry<>(entries);
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
