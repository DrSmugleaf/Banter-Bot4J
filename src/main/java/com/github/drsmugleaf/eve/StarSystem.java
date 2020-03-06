package com.github.drsmugleaf.eve;

import com.github.drsmugleaf.dijkstra.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 19/05/2018.
 */
public class StarSystem extends Node<StarSystem> {

    private final int ID;

    StarSystem(int id, String name) {
        super(name);
        ID = id;
    }

    public static Map<Integer, StarSystem> getAll() {
        Map<Integer, StarSystem> starSystems = new HashMap<>();

        EVE.getStargates().asMap().forEach((from, to) -> {
            if (!starSystems.containsKey(from)) {
                StarSystem systemFrom = new StarSystem(from, EVE.getSystems().get(from));

                for (Integer idTo : to) {
                    StarSystem systemTo;
                    if (starSystems.containsKey(idTo)) {
                        systemTo = starSystems.get(idTo);
                        systemFrom.addDestination(systemTo, 1);
                    } else {
                        systemTo = new StarSystem(idTo, EVE.getSystems().get(idTo));
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
                        systemTo = new StarSystem(idTo, EVE.getSystems().get(idTo));
                        starSystems.put(idTo, systemTo);
                    }
                    starSystems.get(from).addDestination(systemTo, 1);
                }
            }
        });

        return starSystems;
    }

    public int getID() {
        return ID;
    }

}
