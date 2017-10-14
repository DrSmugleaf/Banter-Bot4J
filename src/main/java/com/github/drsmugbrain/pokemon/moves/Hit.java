package com.github.drsmugbrain.pokemon.moves;

import javax.annotation.Nonnull;
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

    @Nonnull
    private final String NAME;

    @Nonnull
    private final Boolean MULTIPLE;

    Hit(@Nonnull String name, boolean multiple) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;
        MULTIPLE = multiple;
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
        return NAME;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

    public boolean hitsMultiple() {
        return MULTIPLE;
    }

    private static class Holder {
        static Map<String, Hit> MAP = new HashMap<>();
    }

}
