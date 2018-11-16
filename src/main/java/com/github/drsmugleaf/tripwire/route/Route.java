package com.github.drsmugleaf.tripwire.route;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 24/05/2018.
 */
public class Route {

    @NotNull
    public final SystemGraph GRAPH;

    @NotNull
    private StarSystem ORIGIN;

    @NotNull
    private StarSystem DESTINATION;

    Route(@NotNull SystemGraph graph, @NotNull StarSystem origin, @NotNull StarSystem destination) {
        GRAPH = graph;
        ORIGIN = origin;
        DESTINATION = destination;

        graph.calculateShortestPathFromSource(origin);
    }

    @NotNull
    public StarSystem getOrigin() {
        return ORIGIN;
    }

    public void setOrigin(@NotNull StarSystem origin) {
        ORIGIN = origin;
        recalculate();
    }


    @NotNull
    public StarSystem getDestination() {
        return DESTINATION;
    }

    public void setDestination(@NotNull StarSystem destination) {
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

    @NotNull
    public String info() {
        return size() + " jumps: " + toString();
    }

}
