package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.BanterBot4J;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public class NameResolver {

    @Nonnull
    private static final Map<String, String> KILLER_NAMES = new HashMap<>();

    public static void registerKiller(@Nonnull String originalName) {
        String name = originalName.toLowerCase();
        if (KILLER_NAMES.containsKey(name)) {
            return;
        }

        KILLER_NAMES.put(name, originalName);

        String[] nameArray = name.split(" ");
        if (!nameArray[0].equals(name)) {
            name = nameArray[0];
        }

        if (!KILLER_NAMES.containsKey(name)) {
            KILLER_NAMES.put(name, originalName);
        }
    }

    @Nullable
    public static String resolveKillerName(@Nullable String name) {
        if (name == null) {
            return null;
        }

        name = name.toLowerCase();

        if (!KILLER_NAMES.containsKey(name)) {
            BanterBot4J.warn("No killer found with name " + name);
            return null;
        }

        return KILLER_NAMES.get(name);
    }

}
