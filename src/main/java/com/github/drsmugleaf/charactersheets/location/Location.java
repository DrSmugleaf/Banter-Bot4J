package com.github.drsmugleaf.charactersheets.location;

import com.github.drsmugleaf.charactersheets.Nameable;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Location implements Nameable {

    private final String NAME;

    public Location(String name) {
        NAME = name;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
