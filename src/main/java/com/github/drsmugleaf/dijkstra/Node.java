package com.github.drsmugleaf.dijkstra;

import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public abstract class Node<T extends Node<T>> {

    @NotNull
    public final String NAME;

    @NotNull
    public final List<T> SHORTEST_PATH = new LinkedList<>();

    @NotNull
    Integer distance = Integer.MAX_VALUE;

    @NotNull
    final Map<T, Integer> ADJACENT_NODES = new HashMap<>();

    public void addDestination(@NotNull T destination, @NotNull Integer distance) {
        ADJACENT_NODES.put(destination, distance);
    }

    protected Node(@NotNull String name) {
        NAME = name;
    }

}
