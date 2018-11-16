package com.github.drsmugleaf.dijkstra;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public abstract class Graph<T extends Node<T>> {

    @NotNull
    public final Set<T> NODES = new HashSet<>();

    protected Graph(@NotNull Collection<T> nodes) {
        NODES.addAll(nodes);
    }

    public void addNode(@NotNull T nodeA) {
        NODES.add(nodeA);
    }

    public void addNode(@NotNull Collection<T> nodes) {
        NODES.addAll(nodes);
    }

    @Nullable
    private T getLowestDistanceNode(@NotNull Set<T> unsettledNodes) {
        T lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (T node : unsettledNodes) {
            if (node.distance < lowestDistance) {
                lowestDistance = node.distance;
                lowestDistanceNode = node;
            }
        }

        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(@NotNull T evaluationNode, @NotNull Integer edgeWeigh, @NotNull T sourceNode) {
        if (sourceNode.distance + edgeWeigh < evaluationNode.distance) {
            evaluationNode.distance = sourceNode.distance + edgeWeigh;
            LinkedList<T> shortestPath = new LinkedList<>(sourceNode.SHORTEST_PATH);
            shortestPath.add(sourceNode);
            evaluationNode.SHORTEST_PATH.clear();
            evaluationNode.SHORTEST_PATH.addAll(shortestPath);
        }
    }

    @NotNull
    public Graph<T> calculateShortestPathFromSource(@NotNull T source) {
        source.distance = 0;
        Set<T> settledNodes = new HashSet<>();
        Set<T> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            T currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);

            if (currentNode == null) {
                continue;
            }

            for (Map.Entry<T, Integer> adjacentNodeEntry : currentNode.ADJACENT_NODES.entrySet()) {
                T adjacentNode = adjacentNodeEntry.getKey();
                Integer edgeWeigh = adjacentNodeEntry.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }

            settledNodes.add(currentNode);
        }

        return this;
    }

}
