package com.github.drsmugleaf.eve;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.dijkstra.Graph;
import com.google.common.collect.ImmutableBiMap;

import java.util.Collection;
import java.util.Map;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public class SystemGraph extends Graph<StarSystem> {

    private SystemGraph(Collection<StarSystem> systems) {
        super(systems);
    }

    @Nullable
    public static Route getRoute(String from, String to) {
        ImmutableBiMap<String, Integer> systems = EVE.getSystems().inverse();
        Integer fromId = systems.get(from);
        Integer toId = systems.get(to);
        Map<Integer, StarSystem> systemMap = StarSystem.getAll();
        SystemGraph graph = new SystemGraph(systemMap.values());
        StarSystem origin = systemMap.get(fromId);
        StarSystem destination = systemMap.get(toId);

        for (StarSystem node : graph.NODES) {
            if (node.NAME.equalsIgnoreCase(from)) {
                origin = node;
            }

            if (node.NAME.equalsIgnoreCase(to)) {
                destination = node;
            }
        }

        if (origin == null || destination == null) {
            return null;
        }

        return new Route(graph, origin, destination);
    }

}
