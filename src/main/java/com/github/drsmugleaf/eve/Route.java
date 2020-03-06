package com.github.drsmugleaf.eve;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 24/05/2018.
 */
public class Route {

    public final SystemGraph GRAPH;
    private StarSystem ORIGIN;
    private StarSystem DESTINATION;

    Route(SystemGraph graph, StarSystem origin, StarSystem destination) {
        GRAPH = graph;
        ORIGIN = origin;
        DESTINATION = destination;

        graph.calculateShortestPathFromSource(origin);
    }

    public StarSystem getOrigin() {
        return ORIGIN;
    }

    public void setOrigin(StarSystem origin) {
        ORIGIN = origin;
        recalculate();
    }

    public StarSystem getDestination() {
        return DESTINATION;
    }

    public void setDestination(StarSystem destination) {
        DESTINATION = destination;
    }

    public void recalculate() {
        GRAPH.calculateShortestPathFromSource(ORIGIN);
    }

    @Override
    public String toString() {
        List<String> route = DESTINATION.SHORTEST_PATH.stream().map(system -> system.NAME).collect(Collectors.toList());
        return String.join(", ", route);
    }

    public int size() {
        return DESTINATION.SHORTEST_PATH.size();
    }

    public String info() {
        return size() + " jumps: " + toString();
    }

}
