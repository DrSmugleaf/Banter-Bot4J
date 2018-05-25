package com.github.drsmugleaf.tripwire.route;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 24/05/2018.
 */
public class Route {

    @Nonnull
    public final SystemGraph GRAPH;

    @Nonnull
    private StarSystem ORIGIN;

    @Nonnull
    private StarSystem DESTINATION;

    Route(@Nonnull SystemGraph graph, @Nonnull StarSystem origin, @Nonnull StarSystem destination) {
        GRAPH = graph;
        ORIGIN = origin;
        DESTINATION = destination;

        graph.calculateShortestPathFromSource(origin);
    }

    @Nonnull
    public StarSystem getOrigin() {
        return ORIGIN;
    }

    public void setOrigin(@Nonnull StarSystem origin) {
        ORIGIN = origin;
        recalculate();
    }


    @Nonnull
    public StarSystem getDestination() {
        return DESTINATION;
    }

    public void setDestination(@Nonnull StarSystem destination) {
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

    @Nonnull
    public String info() {
        return size() + " jumps: " + toString();
    }

}
