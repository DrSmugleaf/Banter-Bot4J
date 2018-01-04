package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.pokemon.IModifier;
import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.InvalidGenerationException;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.trainer.Trainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 01/10/2017.
 */
public enum Tags implements IModifier {

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
    DOOM_DESIRE("Doom Desire", 2) {
        @Override
        public void remove(@Nonnull Pokemon pokemon, @Nonnull Action action) {
            Action tagAction = pokemon.TAGS.get(this);

            Pokemon tagTargetPokemon = tagAction.getTargetPokemon();
            int tagTargetPosition = tagAction.getTargetPosition();
            Trainer tagTargetTeam = tagAction.getTargetTeam();
            Pokemon target = tagTargetTeam.getActivePokemon(tagTargetPosition);

            if (target == null || target.TAGS.containsKey(DOOM_DESIRE_VICTIM)) {
                super.remove(pokemon, action);
                return;
            }

            int damage;
            switch (action.getGeneration()) {
                case I:
                case II:
                case III:
                case IV:
                    damage = BaseMove.DOOM_DESIRE.getDamage(pokemon, tagTargetPokemon, action);
                    break;
                case V:
                case VI:
                case VII:
                    damage = BaseMove.DOOM_DESIRE.getDamage(pokemon, target, action);
                    break;
                default:
                    throw new InvalidGenerationException(action.getGeneration());
            }
            target.damage(damage);
            target.TAGS.put(DOOM_DESIRE_VICTIM, action);

            super.remove(pokemon, action);
        }
    },
    DOOM_DESIRE_VICTIM("Doom Desire Victim", 0);

    private final String NAME;
    private final int DURATION;

    Tags(@Nonnull String name, int duration) {
        NAME = name;
        DURATION = duration;
    }

    Tags(@Nonnull String name) {
        this(name, Integer.MAX_VALUE);
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

    public int getDuration() {
        return DURATION;
    }

    public void apply(@Nonnull Pokemon pokemon, @Nullable Action action) {
        pokemon.TAGS.put(this, action);
    }

    public void remove(@Nonnull Pokemon pokemon) {
        pokemon.TAGS.remove(this);
    }

    public void remove(@Nonnull Pokemon pokemon, @Nonnull Action action) {
        pokemon.TAGS.remove(this);
    }

}
