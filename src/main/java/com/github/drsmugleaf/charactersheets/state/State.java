package com.github.drsmugleaf.charactersheets.state;

import com.github.drsmugleaf.charactersheets.Nameable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class State implements Nameable, Comparable<State> {

    private final String NAME;

    public State(String name) {
        NAME = name;
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

    @Override
    public int hashCode() {
        return Objects.hash(NAME);
    }

    @Override
    public int compareTo(@NotNull State o) {
        return NAME.compareTo(o.NAME);
    }

}
