package com.github.drsmugleaf.deadbydaylight;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public enum Tiers {

    S("S", new Color(60, 0, 60, 127)),
    A("A", new Color(0, 60, 0, 127)),
    B("B", new Color(0, 60, 60, 127)),
    C("C", new Color(60, 60, 0, 127)),
    D("D", new Color(60, 0, 0, 127));

    @Nonnull
    public final String NAME;

    @Nonnull
    public final Color COLOR;

    Tiers(@Nonnull String name, @Nonnull Color color) {
        NAME = name;
        COLOR = color;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
