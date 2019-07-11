package com.github.drsmugleaf.charactersheets.character;

import com.github.drsmugleaf.charactersheets.Nameable;
import com.github.drsmugleaf.charactersheets.character.sheet.Sheet;
import com.github.drsmugleaf.charactersheets.location.Location;
import com.github.drsmugleaf.charactersheets.state.State;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Character implements Nameable {

    private final String NAME;
    private Sheet SHEET;
    private State STATE;
    private Location LOCATION;

    public Character(String name, Sheet sheet, State state, Location location) {
        NAME = name;
        SHEET = sheet;
        STATE = state;
        LOCATION = location;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public Sheet getSheet() {
        return SHEET;
    }

    public State getState() {
        return STATE;
    }

    public Character setState(State state) {
        STATE = state;
        return this;
    }

    public Location getLocation() {
        return LOCATION;
    }

    public Character setLocation(Location location) {
        LOCATION = location;
        return this;
    }

}
