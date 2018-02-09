package com.github.drsmugleaf.chemistry.organic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 09/02/2018.
 */
public enum Elements {

    CARBON("C"),
    HYDROGEN("H");

    public final String SYMBOL;

    Elements(@Nonnull String symbol) {
        SYMBOL = symbol;
        Holder.MAP.put(symbol.toLowerCase(), this);
    }

    @Nullable
    public static Elements get(@Nonnull String symbol) {
        return Holder.MAP.get(symbol.toLowerCase());
    }

    private static class Holder {
        static final Map<String, Elements> MAP = new HashMap<>();
    }

}
