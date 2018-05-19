package com.github.drsmugleaf.dijkstra;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public abstract class Node<T extends Node<T>> {

    @Nonnull
    public final String NAME;

    @Nonnull
    final List<T> SHORTEST_PATH = new LinkedList<>();

    @Nonnull
    Integer distance = Integer.MAX_VALUE;

    @Nonnull
    final Map<T, Integer> ADJACENT_NODES = new HashMap<>();

    public void addDestination(@Nonnull T destination, @Nonnull Integer distance) {
        ADJACENT_NODES.put(destination, distance);
    }

    public Node(@Nonnull String name) {
        NAME = name;
    }

}
