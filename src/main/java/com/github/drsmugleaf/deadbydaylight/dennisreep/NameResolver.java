package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.Survivors;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public class NameResolver {

    private static Map<String, String> SURVIVOR_NAMES = new HashMap<>();

    static {
        List<String> namesSeen = new ArrayList<>();
        for (Survivors survivor : Survivors.values()) {
            String name = survivor.NAME.toLowerCase();
            if (namesSeen.contains(name)) {
                throw new IllegalStateException("Found a repeated full survivor name: " + name);
            }

            SURVIVOR_NAMES.put(name, survivor.name());

            String[] nameArray = name.split(" ");
            if (nameArray[0].equals(name)) {
                continue;
            }

            name = nameArray[0];

            if (namesSeen.contains(name)) {
                SURVIVOR_NAMES.remove(name);
            } else {
                SURVIVOR_NAMES.put(name, survivor.name());
            }

            namesSeen.add(name);
        }
    }

    public static String resolveSurvivorName(@Nonnull String name) {
        return SURVIVOR_NAMES.get(name.toLowerCase());
    }

}
