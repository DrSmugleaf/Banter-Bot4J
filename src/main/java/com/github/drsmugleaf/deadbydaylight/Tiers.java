package com.github.drsmugleaf.deadbydaylight;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public enum Tiers {

    S("S"),
    A("A"),
    B("B"),
    C("C"),
    D("D");

    @Nonnull
    public final String NAME;

    Tiers(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
