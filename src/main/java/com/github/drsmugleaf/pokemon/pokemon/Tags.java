package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.battle.Action;
import com.github.drsmugleaf.pokemon.battle.IModifier;
import com.github.drsmugleaf.pokemon.battle.InvalidGenerationException;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 01/10/2017.
 */
public enum Tags implements IModifier {

    BERRY_USED("Berry Used"),
    DESTINY_BOND("Destiny Bond") {
        @Override
        public void onOwnFaint(@Nullable Pokemon attacker, Pokemon defender) {
            if (attacker != null && !defender.isAlly(attacker)) {
                attacker.kill();
            }
        }

        @Override
        public void onOwnTurnStart(Pokemon pokemon) {
            remove(pokemon);
        }
    },
    DOOM_DESIRE("Doom Desire", 2) {
        @Override
        protected void remove(Pokemon pokemon, Action action) {
            Action tagAction = get(pokemon).getAction();

            Pokemon tagTargetPokemon = tagAction.getTargetPokemon();
            int tagTargetPosition = tagAction.getTargetPosition();
            Trainer tagTargetTeam = tagAction.getTargetTeam();
            Pokemon target = tagTargetTeam.getActivePokemon(tagTargetPosition);

            if (target == null || DOOM_DESIRE_VICTIM.has(target)) {
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
            DOOM_DESIRE_VICTIM.apply(target, action);

            super.remove(pokemon, action);
        }
    },
    DOOM_DESIRE_VICTIM("Doom Desire Victim", 0);

    public final String NAME;
    private final int DURATION;

    Tags(String name, int duration) {
        NAME = name;
        DURATION = duration;
    }

    Tags(String name) {
        this(name, Integer.MAX_VALUE);
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

    @Contract(pure = true)
    public int getDuration() {
        return DURATION;
    }

    public void apply(Pokemon pokemon, @Nullable Action action) {
        Tag tag = new Tag(this, action);
        pokemon.TAGS.add(tag);
    }

    @Nullable
    public Tag get(Pokemon pokemon) {
        for (Tag tag : pokemon.TAGS) {
            if (tag.getBaseTag() == this) {
                return tag;
            }
        }

        return null;
    }

    public boolean has(Pokemon pokemon) {
        for (Tag tag : pokemon.TAGS) {
            if (tag.getBaseTag() == this) {
                return true;
            }
        }

        return false;
    }

    protected void remove(Pokemon pokemon) {
        for (Tag tag1 : pokemon.TAGS) {
            if (tag1.getBaseTag() == this) {
                pokemon.TAGS.remove(tag1);
            }
        }
    }

    protected void remove(Pokemon pokemon, Action action) {
        for (Tag tag : pokemon.TAGS) {
            if (tag.getBaseTag() == this && tag.getAction() == action) {
                pokemon.TAGS.remove(tag);
            }
        }
    }

}
