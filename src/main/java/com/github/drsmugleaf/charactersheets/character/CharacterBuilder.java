package com.github.drsmugleaf.charactersheets.character;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.charactersheets.Builder;
import com.github.drsmugleaf.charactersheets.character.sheet.SheetBuilder;
import com.github.drsmugleaf.charactersheets.location.Location;
import com.github.drsmugleaf.charactersheets.state.State;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class CharacterBuilder implements Builder<Character> {

    @Nullable
    private String name;
    private SheetBuilder sheet = new SheetBuilder();
    @Nullable
    private State state;
    @Nullable
    private Location location;

    public CharacterBuilder() {}

    @Nullable
    public String getName() {
        return name;
    }

    public CharacterBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SheetBuilder getSheet() {
        return sheet;
    }

    @Nullable
    public State getState() {
        return state;
    }

    public CharacterBuilder setState(State state) {
        this.state = state;
        return this;
    }

    @Nullable
    public Location getLocation() {
        return location;
    }

    public CharacterBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    @Override
    public Character build() {
        return new Character(name, sheet.build(), state, location);
    }

}
