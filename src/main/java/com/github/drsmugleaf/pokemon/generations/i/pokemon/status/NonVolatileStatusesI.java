package com.github.drsmugleaf.pokemon.generations.i.pokemon.status;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.base.pokemon.modifier.IModifiers;
import com.github.drsmugleaf.pokemon.base.pokemon.state.PokemonStates;
import com.github.drsmugleaf.pokemon.base.pokemon.status.INonVolatileStatus;
import com.github.drsmugleaf.pokemon.generations.i.pokemon.IBattlePokemonI;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum NonVolatileStatusesI implements INonVolatileStatus<IBattlePokemonI> {
    // TODO: 20-Nov-19 Add type immunities
    SLEEP("Sleep") {
        @Override
        public Integer getDuration() {
            return ThreadLocalRandom.current().nextInt(1, 7 + 1);
        }

        @Override
        protected void applyEffect(IBattlePokemonI pokemon) {
            pokemon.getModifiers().getAllowed().addUnique(PokemonStates.ATTEMPTING_MOVE, this, () -> false);
        }
    },
    POISON("Poison") {
        @Override
        protected void applyEffect(IBattlePokemonI pokemon) {
            pokemon.getModifiers().getExecutable().addUnique(PokemonStates.AFTER_MOVE, this, () -> pokemon.damage(1.0 / 16.0));
        }
    },
    BURN("Burn") {
        @Override
        protected void applyEffect(IBattlePokemonI pokemon) {
            IModifiers mod = pokemon.getModifiers();
            mod.getExecutable().addUnique(PokemonStates.AFTER_MOVE, this, () -> pokemon.damage(1.0 / 16.0));
            mod.getMultiplier().addUnique(PokemonStates.CALCULATING_ATTACK, this, () -> 0.5);
        }
    },
    FREEZE("Freeze") {
        @Override
        protected void applyEffect(IBattlePokemonI pokemon) {
            IModifiers mod = pokemon.getModifiers();
            mod.getAllowed().addUnique(PokemonStates.ATTEMPTING_MOVE, this, () -> false);
            mod.getExecutable().addUnique(PokemonStates.RECEIVING_MOVE, this, () -> {
                boolean hitByFire = pokemon.getBattle().getTurn().getMove().getMove().getType().getName().equalsIgnoreCase("FIRE");
                boolean isFireSpin = pokemon.getBattle().getTurn().getMove().getMove().getEffect().getID() == 43;
                if (hitByFire && !isFireSpin) {
                    remove(pokemon);
                }
            });
        }
    },
    PARALYSIS("Paralysis") {
        @Override
        protected void applyEffect(IBattlePokemonI pokemon) {
            IModifiers mod = pokemon.getModifiers();
            mod.getMultiplier().addUnique(PokemonStates.CALCULATING_SPEED, this, () -> 0.25);
            mod.getAllowed().addUnique(PokemonStates.ATTEMPTING_MOVE, this, () -> ThreadLocalRandom.current().nextDouble() < 0.25);
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
    public final void apply(IBattlePokemonI pokemon) {
        pokemon.setStatus(this);
        applyEffect(pokemon);
    }

    protected abstract void applyEffect(IBattlePokemonI pokemon);

    @Override
    public void remove(IBattlePokemonI pokemon) {
        pokemon.getModifiers().removeAll(this);
        pokemon.setStatus(null);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
