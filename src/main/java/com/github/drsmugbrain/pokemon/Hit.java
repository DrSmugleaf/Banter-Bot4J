package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 17/06/2017.
 */
public enum Hit {

    SELECTED_TARGET("Selected Target"),
    ADJACENT_FOES("All Adjacent Foes"),
    SELF("Self"),
    SELF_OR_ALLY("Self or Ally"),
    RANDOM_TARGET("Random Target"),
    FIELD("Field"),
    ENEMY_SIDE("Enemy Side"),
    TEAM("Team"),
    ALLY("Ally"),
    ALL_ADJACENT("All Adjacent Pokémon"),
    ALL("All"),
    ADJACENT_ALLY("Adjacent Ally"),
    SPECIAL("Special");

    private final String NAME;

    Hit(@Nonnull String name) {
        Holder.MAP.put(name.toLowerCase(), this);
        this.NAME = name;
    }

    public static Hit getHit(@Nonnull String hit) {
        hit = hit.toLowerCase();
        if (!Holder.MAP.containsKey(hit)) {
            throw new NullPointerException("Hit " + hit + " doesn't exist");
        }

        return Holder.MAP.get(hit);
    }

    @Override
    public String toString() {
        return this.NAME;
    }

    public String getName() {
        return this.NAME;
    }

    private static class Holder {
        static Map<String, Hit> MAP = new HashMap<>();
    }

}
