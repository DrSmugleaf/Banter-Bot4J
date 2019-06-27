package com.github.drsmugleaf.pokemon.moves;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 17/06/2017.
 */
public enum Target {

    ANY_ADJACENT("Any Adjacent"),
    ALL_ADJACENT("All Adjacent"),
    ADJACENT_FOES("Adjacent Foes"),
    ALL("All"),
    ALL_BUT_SELF("All but self"),
    ALL_FOES("All Foes"),
    ALL_ALLIES("All Allies"),
    DEFAULT("Default"),
    SELF("Self"),
    SELF_OR_ADJACENT_ALLY("Self or adjacent ally"),
    ADJACENT_ALLY("Adjacent ally");

    public final String NAME;

    Target(String name) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;
    }

    public static Target getTarget(String target) {
        target = target.toLowerCase();
        if (!Holder.MAP.containsKey(target)) {
            throw new NullPointerException("Target " + target + " doesn't exist");
        }

        return Holder.MAP.get(target);
    }

    @Override
    public String toString() {
        return NAME;
    }

    public String getName() {
        return NAME;
    }

    private static class Holder {
        static Map<String, Target> MAP = new HashMap<>();
    }


}
