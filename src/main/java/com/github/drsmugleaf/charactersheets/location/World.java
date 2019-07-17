package com.github.drsmugleaf.charactersheets.location;

import com.github.drsmugleaf.charactersheets.Nameable;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class World implements Nameable {

    private final String NAME;
    private final ImmutableMap<String, Location> LOCATIONS;

    public World(String name, Map<String, Location> locations) {
        NAME = name;
        LOCATIONS = ImmutableMap.copyOf(locations);
    }

    @Override
    public String getName() {
        return NAME;
    }

    public ImmutableMap<String, Location> get() {
        return LOCATIONS;
    }

    public Location get(String name) {
        return LOCATIONS.get(name);
    }

}
