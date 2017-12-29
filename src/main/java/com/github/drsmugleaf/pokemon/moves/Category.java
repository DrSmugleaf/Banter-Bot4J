package com.github.drsmugleaf.pokemon.moves;

import com.github.drsmugleaf.pokemon.stats.PermanentStat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public enum Category {

    PHYSICAL("Physical", PermanentStat.ATTACK),
    SPECIAL("Special", PermanentStat.SPECIAL_ATTACK),
    OTHER("Other", null);

    @Nonnull
    private final String NAME;

    @Nullable
    private final PermanentStat STAT;

    Category(@Nonnull String name, @Nullable PermanentStat stat) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;
        STAT = stat;
    }

    @Nonnull
    public static Category getCategory(@Nonnull String category) {
        category = category.toLowerCase();

        if (Objects.equals(category, "non-damaging")) {
            return Holder.MAP.get("other");
        }

        if (!Holder.MAP.containsKey(category)) {
            throw new NullPointerException("Category " + category + " doesn't exist");
        }

        return Holder.MAP.get(category);
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

    @Nullable
    public PermanentStat getStat() {
        return STAT;
    }

    private static class Holder {
        static Map<String, Category> MAP = new HashMap<>();
    }

}
