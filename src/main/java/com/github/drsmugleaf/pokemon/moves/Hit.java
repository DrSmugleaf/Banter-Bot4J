package com.github.drsmugleaf.pokemon.moves;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 17/06/2017.
 */
public enum Hit {

    SELECTED_TARGET("Selected Target", false),
    ADJACENT_FOES("All Adjacent Foes", true),
    SELF("Self", false),
    SELF_OR_ALLY("Self or Ally", false),
    RANDOM_TARGET("Random Target", false),
    FIELD("Field", true),
    ENEMY_SIDE("Enemy Side", true),
    TEAM("Team", true),
    ALLY("Ally", false),
    ALL_ADJACENT("All Adjacent Pokémon", true),
    ALL("All", true),
    ADJACENT_ALLY("Adjacent Ally", false),
    SPECIAL("Special", false);

    public final String NAME;
    public final boolean MULTIPLE;

    Hit(String name, boolean multiple) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;
        MULTIPLE = multiple;
    }

    public static Hit getHit(String hit) {
        hit = hit.toLowerCase();

        if (!Holder.MAP.containsKey(hit)) {
            throw new NullPointerException("Hit " + hit + " doesn't exist");
        }

        return Holder.MAP.get(hit);
    }

    @Contract(pure = true)
    @Override
    public String toString() {
        return NAME;
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

    @Contract(pure = true)
    public boolean hitsMultiple() {
        return MULTIPLE;
    }

    private static class Holder {
        static Map<String, Hit> MAP = new HashMap<>();
    }

}
