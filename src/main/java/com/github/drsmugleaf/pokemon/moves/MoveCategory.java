package com.github.drsmugleaf.pokemon.moves;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import org.jetbrains.annotations.Contract;

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

    public final String NAME;
    @Nullable
    public final PermanentStat STAT;

    MoveCategory(String name, @Nullable PermanentStat stat) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;
        STAT = stat;
    }

    public static MoveCategory getCategory(String category) {
        category = category.toLowerCase();

        if (Objects.equals(category, "non-damaging")) {
            return Holder.MAP.get("other");
        }

        if (!Holder.MAP.containsKey(category)) {
            throw new NullPointerException("Move category " + category + " doesn't exist");
        }

        return Holder.MAP.get(category);
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

    @Contract(pure = true)
    @Nullable
    public PermanentStat getStat() {
        return STAT;
    }

    private static class Holder {
        static Map<String, MoveCategory> MAP = new HashMap<>();
    }

}
