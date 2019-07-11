package com.github.drsmugleaf.charactersheets.state;

import com.github.drsmugleaf.charactersheets.Nameable;

import java.util.Objects;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class State implements Nameable {

    private final String NAME;

    public State(String name) {
        this.NAME = name;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(NAME, state.NAME);
    }

}
