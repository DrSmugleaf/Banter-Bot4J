package com.github.drsmugleaf.dijkstra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public abstract class Node<T extends Node<T>> {

    public final String NAME;
    public final List<T> SHORTEST_PATH = new LinkedList<>();

    Integer distance = Integer.MAX_VALUE;

    final Map<T, Integer> ADJACENT_NODES = new HashMap<>();

    public void addDestination(T destination, Integer distance) {
        ADJACENT_NODES.put(destination, distance);
    }

    protected Node(String name) {
        NAME = name;
    }

}
