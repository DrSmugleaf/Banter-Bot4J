package com.github.drsmugleaf.pokemon2.generations.i.pokemon.status;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.modifier.IExecutableModifier;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.PokemonStates;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.INonVolatileStatus;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum NonVolatileStatusesI implements INonVolatileStatus<IBattlePokemon<?>> {

    SLEEP("Sleep") {
        @Override
        public Integer getDuration() {
            return ThreadLocalRandom.current().nextInt(1, 7 + 1);
        }

        @Override
        public void apply(IBattlePokemon<?> pokemon) {
            pokemon.getModifiers().addAllowedUnique(PokemonStates.ATTEMPTING_MOVE, this, () -> false);
        }
    },
    POISON("Poison") {
        @Override
        public void apply(IBattlePokemon<?> pokemon) {
            pokemon.getModifiers().addExecutableUnique(PokemonStates.AFTER_MOVE, this, (IExecutableModifier) () -> pokemon.damage(1.0 / 16.0));
        }
    },
    BURN("Burn") {
        @Override
        public void apply(IBattlePokemon<?> pokemon) {
            pokemon.getModifiers().addExecutableUnique(PokemonStates.AFTER_MOVE, this, (IExecutableModifier) () -> pokemon.damage(1.0 / 16.0));
        }
    };

    @Nullable
    private final Integer DURATION;
    private final String NAME;

    NonVolatileStatusesI(@Nullable Integer duration, String name) {
        DURATION = duration;
        NAME = name;
    }

    NonVolatileStatusesI(String name) {
        this(null, name);
    }

    @Nullable
    @Override
    public Integer getDuration() {
        return DURATION;
    }

    @Override
    public void apply(IBattlePokemon<?> pokemon) {
        pokemon.setStatus(this);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
