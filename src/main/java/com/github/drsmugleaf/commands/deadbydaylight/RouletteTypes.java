package com.github.drsmugleaf.commands.deadbydaylight;

import com.github.drsmugleaf.Nullable;

/**
 * Created by DrSmugleaf on 21/04/2019
 */
public enum RouletteTypes {

    KILLER,
    SURVIVOR,
    FRIENDS;

    @Nullable
    public static RouletteTypes from(String name) {
        name = name.toLowerCase();

        for (RouletteTypes type : values()) {
            if (type.name().toLowerCase().contains(name)) {
                return type;
            }
        }

        return null;
    }

}
