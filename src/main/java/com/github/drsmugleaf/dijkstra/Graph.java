package com.github.drsmugleaf.dijkstra;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public abstract class Graph {

    @Nonnull
    private final Set<Node> NODES = new HashSet<>();

    public void addNode(@Nonnull Node nodeA) {
        NODES.add(nodeA);
    }

    @Nullable
    private static Node getLowestDistanceNode(@Nonnull Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (Node node : unsettledNodes) {
            if (node.distance < lowestDistance) {
                lowestDistance = node.distance;
                lowestDistanceNode = node;
            }
        }

        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(@Nonnull Node evaluationNode, @Nonnull Integer edgeWeigh, @Nonnull Node sourceNode) {
        if (sourceNode.distance + edgeWeigh < evaluationNode.distance) {
            evaluationNode.distance = sourceNode.distance + edgeWeigh;
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.SHORTEST_PATH);
            shortestPath.add(sourceNode);
            evaluationNode.SHORTEST_PATH.clear();
            evaluationNode.SHORTEST_PATH.addAll(shortestPath);
        }
    }

    @Nonnull
    public static Graph calculateShortestPathFromSource(@Nonnull Graph graph, @Nonnull Node source) {
        source.distance = 0;
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);

            if (currentNode == null) {
                continue;
            }

            for (Map.Entry<Node, Integer> adjacentNodeEntry : currentNode.ADJACENT_NODES.entrySet()) {
                Node adjacentNode = adjacentNodeEntry.getKey();
                Integer edgeWeigh = adjacentNodeEntry.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }

            settledNodes.add(currentNode);
        }

        return graph;
    }

}
