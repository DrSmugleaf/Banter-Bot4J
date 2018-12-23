package com.github.drsmugleaf.tripwire.route;

import com.github.drsmugleaf.dijkstra.Graph;
import com.github.drsmugleaf.tripwire.API;
import com.github.drsmugleaf.tripwire.models.Signature;
import com.github.drsmugleaf.tripwire.models.Wormhole;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public class SystemGraph extends Graph<StarSystem> {

    private final Set<StarSystem> NODES;

    SystemGraph(Collection<StarSystem> systems) {
        NODES = new HashSet<>(systems);
    }

    @NotNull
    static SystemGraph fromSignaturesAndWormholes(@NotNull Map<Integer, Signature> signatures, @NotNull List<Wormhole> wormholes) {
        Map<Integer, StarSystem> systems = StarSystem.fromSignatures(signatures.values());

        for (Wormhole wormhole : wormholes) {
            Signature signature1 = signatures.get(wormhole.INITIAL_ID);
            Signature signature2 = signatures.get(wormhole.SECONDARY_ID);
            StarSystem system1 = systems.get(signature1.SYSTEM_ID);
            StarSystem system2 = systems.get(signature2.SYSTEM_ID);

            system1.addDestination(system2, 1);
            system2.addDestination(system1, 1);
        }

        return new SystemGraph(systems.values());
    }

    @Nullable
    public static Route getRoute(long id, @NotNull String username, @NotNull String password, @NotNull String from, @NotNull String to) {
        String response = API.refresh(id, username, password).body();
        Map<Integer, Signature> signatures = Signature.fromJson(response);
        List<Wormhole> wormholes = Wormhole.fromJson(response);

        signatures.entrySet().removeIf(entry -> !StarSystem.isValid(entry.getValue().SYSTEM_ID));
        wormholes.removeIf(wormhole -> !signatures.containsKey(wormhole.INITIAL_ID) || !signatures.containsKey(wormhole.SECONDARY_ID));
        SystemGraph graph = fromSignaturesAndWormholes(signatures, wormholes);

        StarSystem origin = null;
        StarSystem destination = null;
        for (StarSystem node : graph.getNodes()) {
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

    @NotNull
    @Override
    public Set<StarSystem> getNodes() {
        return NODES;
    }

}
