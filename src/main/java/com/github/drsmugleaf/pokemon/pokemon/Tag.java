package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.pokemon.IModifier;
import com.github.drsmugleaf.pokemon.battle.Action;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 01/10/2017.
 */
public enum Tag implements IModifier {

    BERRY_USED("Berry Used"),
    DESTINY_BOND("Destiny Bond") {
        @Override
        public void onOwnFaint(@Nullable Pokemon attacker, @Nonnull Pokemon defender) {
            if (attacker != null && !defender.isAlly(attacker)) {
                attacker.kill();
            }
        }

        @Override
        public void onOwnTurnStart(@Nonnull Pokemon pokemon) {
            remove(pokemon);
        }
    },
    DOOM_DESIRE("Doom Desire");

    private final String NAME;

    Tag(@Nonnull String name) {
        NAME = name;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

    public void apply(@Nonnull Pokemon pokemon, @Nullable Action action) {
        pokemon.TAGS.put(this, action);
    }

    public void remove(@Nonnull Pokemon pokemon) {
        pokemon.TAGS.remove(this);
    }

}
