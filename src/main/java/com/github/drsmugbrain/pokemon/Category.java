package com.github.drsmugbrain.pokemon;

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

    private final String NAME;
    private final PermanentStat STAT;

    Category(@Nonnull String name, @Nullable PermanentStat stat) {
        Holder.MAP.put(name.toLowerCase(), this);
        this.NAME = name;
        this.STAT = stat;
    }

    @Nonnull
    public static Category getCategory(@Nonnull String category) {
        category = category.toLowerCase();
        if (Objects.equals(category, "non-damaging")) return Holder.MAP.get("other");
        if (!Holder.MAP.containsKey(category)) {
            throw new NullPointerException("Category " + category + " doesn't exist");
        }

        return Holder.MAP.get(category);
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nullable
    public PermanentStat getStat() {
        return this.STAT;
    }

    private static class Holder {
        static Map<String, Category> MAP = new HashMap<>();
    }

}
