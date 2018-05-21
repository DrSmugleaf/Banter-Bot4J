package com.github.drsmugleaf.tripwire;

import com.github.drsmugleaf.dijkstra.Node;
import com.github.drsmugleaf.eve.Systems;
import com.github.drsmugleaf.eve.esi.ApiException;
import com.github.drsmugleaf.eve.esi.api.UniverseApi;
import com.github.drsmugleaf.eve.esi.model.PostUniverseNames200Ok;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public class StarSystem extends Node<StarSystem> {

    final int ID;

    StarSystem(int id, @Nonnull String name) {
        super(name);
        ID = id;
    }

    public static boolean isValid(@Nullable Integer id) {
        return id != null && id > 30000001;
    }

    @Nonnull
    static Map<Integer, StarSystem> fromIDs(@Nonnull Collection<Integer> IDs) {
        Map<Integer, StarSystem> starSystems = new HashMap<>();
        IDs.removeIf(id -> !StarSystem.isValid(id));

        try {
            List<PostUniverseNames200Ok> response = new UniverseApi().postUniverseNames(new ArrayList<>(IDs), null, null, null);
            for (PostUniverseNames200Ok systemInfo : response) {
                StarSystem system = new StarSystem(systemInfo.getId(), systemInfo.getName());
                starSystems.put(system.ID, system);
            }
        } catch (ApiException e) {
            throw new ESIException("Error getting names for a list of systems " + IDs + " response body: " + e.getResponseBody(), e);
        }

        Systems.CONNECTIONS.asMap().forEach((from, to) -> {
            if (!starSystems.containsKey(from)) {
                StarSystem systemFrom = new StarSystem(from, Systems.NAMES.get(from));

                for (Integer idTo : to) {
                    StarSystem systemTo;
                    if (starSystems.containsKey(idTo)) {
                        systemTo = starSystems.get(idTo);
                        systemFrom.addDestination(systemTo, 1);
                    } else {
                        systemTo = new StarSystem(idTo, Systems.NAMES.get(idTo));
                        starSystems.put(idTo, systemTo);
                    }
                    systemFrom.addDestination(systemTo, 1);
                }

                starSystems.put(from, systemFrom);
            } else {
                for (Integer idTo : to) {
                    StarSystem systemTo;
                    if (starSystems.containsKey(idTo)) {
                        systemTo = starSystems.get(idTo);
                    } else {
                        systemTo = new StarSystem(idTo, Systems.NAMES.get(idTo));
                        starSystems.put(idTo, systemTo);
                    }
                    starSystems.get(from).addDestination(systemTo, 1);
                }
            }
        });

        return starSystems;
    }

    @Nonnull
    static Map<Integer, StarSystem> fromSignatures(@Nonnull Collection<Signature> signatures) {
        Set<Integer> ids = signatures.stream().map(signature -> signature.SYSTEM_ID).collect(Collectors.toSet());
        return fromIDs(ids);
    }

}
