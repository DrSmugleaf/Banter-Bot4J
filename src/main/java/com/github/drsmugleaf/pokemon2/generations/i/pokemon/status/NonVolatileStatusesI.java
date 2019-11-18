package com.github.drsmugleaf.pokemon2.generations.i.pokemon.status;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.battle.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.PokemonStates;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.INonVolatileStatus;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum NonVolatileStatusesI implements INonVolatileStatus<IBattlePokemon<?>> {

    SLEEP(false, "Sleep") {
        @Override
        public Integer getDuration() {
            return ThreadLocalRandom.current().nextInt(1, 7 + 1);
        }

        @Override
        public void apply(IBattlePokemon<?> pokemon) {
            pokemon.addModifier(PokemonStates.ATTEMPTING_MOVE, this, () -> false);
        }
    };

    private final boolean IS_VOLATILE;
    @Nullable
    private final Integer DURATION;
    private final String NAME;

    NonVolatileStatusesI(boolean isVolatile, @Nullable Integer duration, String name) {
        IS_VOLATILE = isVolatile;
        DURATION = duration;
        NAME = name;
    }

    NonVolatileStatusesI(boolean isVolatile, String name) {
        this(isVolatile, null, name);
    }

    @Override
    public boolean isVolatileStatus() {
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
