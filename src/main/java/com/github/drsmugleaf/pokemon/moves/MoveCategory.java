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
public enum MoveCategory {

    PHYSICAL("Physical", PermanentStat.ATTACK),
    SPECIAL("Special", PermanentStat.SPECIAL_ATTACK),
    OTHER("Other", null);

    @Nonnull
    public final String NAME;

    @Nullable
    public final PermanentStat STAT;

    MoveCategory(@Nonnull String name, @Nullable PermanentStat stat) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;
        STAT = stat;
    }

    @Nonnull
    public static MoveCategory getCategory(@Nonnull String category) {
        category = category.toLowerCase();

        if (Objects.equals(category, "non-damaging")) {
            return Holder.MAP.get("other");
        }

        if (!Holder.MAP.containsKey(category)) {
            throw new NullPointerException("Move category " + category + " doesn't exist");
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
        @Nonnull
        static Map<String, MoveCategory> MAP = new HashMap<>();
    }

}
