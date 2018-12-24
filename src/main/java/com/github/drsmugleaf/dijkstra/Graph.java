package com.github.drsmugleaf.dijkstra;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public abstract class Graph<T extends Node<T>> {

    @Nullable
    private T getLowestDistanceNode(@Nonnull Set<T> unsettledNodes) {
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

    private void calculateMinimumDistance(@Nonnull T evaluationNode, @Nonnull Integer edgeWeigh, @Nonnull T sourceNode) {
        if (sourceNode.distance + edgeWeigh < evaluationNode.distance) {
            evaluationNode.distance = sourceNode.distance + edgeWeigh;
            LinkedList<T> shortestPath = new LinkedList<>(sourceNode.SHORTEST_PATH);
            shortestPath.add(sourceNode);
            evaluationNode.SHORTEST_PATH.clear();
            evaluationNode.SHORTEST_PATH.addAll(shortestPath);
        }
    }

    @Nonnull
    public Graph<T> calculateShortestPathFromSource(@Nonnull T source) {
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

    @NotNull
    public abstract Set<T> getNodes();

}
