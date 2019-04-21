package com.github.drsmugleaf.commands.deadbydaylight;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 21/04/2019
 */
public enum RouletteTypes {

    KILLER,
    SURVIVOR,
    FRIENDS;

    @Nullable
    public static RouletteTypes from(@Nonnull String name) {
        name = name.toLowerCase();

        for (RouletteTypes type : values()) {
            if (type.name().toLowerCase().contains(name)) {
                return type;
            }
        }

        return null;
    }

}
