package com.github.drsmugleaf.commands.bridge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 23/08/2018
 */
public enum Types {

    JABBER("Jabber");

    @Nonnull
    private final List<String> ALIASES = new ArrayList<>();

    Types(@Nonnull String... aliases) {
        for (String alias : aliases) {
            ALIASES.add(alias.toLowerCase());
        }
    }

    @Nullable
    public static Types from(@Nullable String name) {
        if (name == null) {
            return null;
        }

        name = name.toLowerCase();

        for (Types type : values()) {
            if (type.ALIASES.contains(name)) {
                return type;
            }
        }

        return null;
    }

}
