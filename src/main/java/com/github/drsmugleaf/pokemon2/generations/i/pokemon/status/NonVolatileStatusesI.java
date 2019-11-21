package com.github.drsmugleaf.pokemon2.generations.i.pokemon.status;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.pokemon.IBattlePokemon;
import com.github.drsmugleaf.pokemon2.base.pokemon.state.PokemonStates;
import com.github.drsmugleaf.pokemon2.base.pokemon.status.INonVolatileStatus;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum NonVolatileStatusesI implements INonVolatileStatus<IBattlePokemon<?>> {
    // TODO: 20-Nov-19 Add type immunities
    SLEEP("Sleep") {
        @Override
        public Integer getDuration() {
            return ThreadLocalRandom.current().nextInt(1, 7 + 1);
        }

        @Override
        protected void applyEffect(IBattlePokemon<?> pokemon) {
            pokemon.getModifiers().getAllowed().addUnique(PokemonStates.ATTEMPTING_MOVE, this, () -> false);
        }
    },
    POISON("Poison") {
        @Override
        protected void applyEffect(IBattlePokemon<?> pokemon) {
            pokemon.getModifiers().getExecutable().addUnique(PokemonStates.AFTER_MOVE, this, () -> pokemon.damage(1.0 / 16.0));
        }
    },
    BURN("Burn") {
        @Override
        protected void applyEffect(IBattlePokemon<?> pokemon) {
            pokemon.getModifiers().getExecutable().addUnique(PokemonStates.AFTER_MOVE, this, () -> pokemon.damage(1.0 / 16.0));
            pokemon.getModifiers().getMultiplier().addUnique(PokemonStates.CALCULATING_ATTACK, this, () -> 0.5);
        }
    },
    FREEZE("Freeze") {
        @Override
        protected void applyEffect(IBattlePokemon<?> pokemon) {
            pokemon.getModifiers().getAllowed().addUnique(PokemonStates.ATTEMPTING_MOVE, this, () -> false);
            pokemon.getModifiers().getExecutable().addUnique(PokemonStates.RECEIVING_MOVE, this, () -> {
                boolean hitByFire = pokemon.getBattle().getTurn().getMove().getMove().getType().getName().equalsIgnoreCase("FIRE");
                if (hitByFire) {
                    remove(pokemon);
                }
            });
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
    public final void apply(IBattlePokemon<?> pokemon) {
        pokemon.setStatus(this);
        applyEffect(pokemon);
    }

    protected abstract void applyEffect(IBattlePokemon<?> pokemon);

    @Override
    public void remove(IBattlePokemon<?> pokemon) {
        pokemon.getModifiers().removeAll(this);
        pokemon.setStatus(null);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
