package com.github.drsmugleaf.tripwire;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 16/05/2018.
 */
public enum Masks {

    PANDEMIC_HORDE_INC("Pandemic Horde Inc.", 4537.0);

    @Nonnull
    public final String NAME;

    public final double ID;

    Masks(@Nonnull String name, double id) {
        NAME = name;
        ID = id;
    }

}
