package com.github.drsmugleaf.pokemon2.generations.i.pokemon.status;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.IStatus;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum StatusesI implements IStatus {

    SLEEP(false, "Sleep") {
        @Override
        public Integer getDuration() {
            return ThreadLocalRandom.current().nextInt(1, 7 + 1);
        }
    };

    private final boolean IS_VOLATILE;
    @Nullable
    private final Integer DURATION;
    private final String NAME;

    StatusesI(boolean isVolatile, @Nullable Integer duration, String name) {
        IS_VOLATILE = isVolatile;
        DURATION = duration;
        NAME = name;
    }

    StatusesI(boolean isVolatile, String name) {
        this(isVolatile, null, name);
    }

    @Override
    public boolean isVolatile() {
        return IS_VOLATILE;
    }

    @Override
    public Integer getDuration() {
        return DURATION;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
