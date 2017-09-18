package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 08/06/2017.
 */
public enum Status implements IBattle {

    BURN("Burn") {
        @Override
        protected boolean apply(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
            switch (battle.getGeneration()) {
                case I:
                case II:
                    user.addStatModifier(Stat.ATTACK, "burn", 0.5);
                    break;
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            return super.apply(user, target, battle, trainer, move);
        }

        @Override
        protected void remove(Pokemon pokemon) {
            Generation generation = pokemon.getBattle().getGeneration();

            switch (generation) {
                case I:
                case II:
                    pokemon.removeStatModifier(Stat.ATTACK, "burn");
                    break;
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }

            super.remove(pokemon);
        }

        @Override
        public void onTurnEnd(@Nonnull Battle battle, @Nonnull Pokemon pokemon) {
            switch (battle.getGeneration()) {
                case I:
                    pokemon.damage(6.25);
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                    pokemon.damage(12.5);
                    break;
                case VII:
                    pokemon.damage(6.25);
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }
            pokemon.damage(6.25);
        }

        @Override
        public double damageMultiplier(@Nonnull Pokemon attacker, @Nonnull Action action) {
            Generation generation = attacker.getBattle().getGeneration();

            switch (generation) {
                case I:
                case II:
                    return 1.0;
                case III:
                case IV:
                case V:
                    switch (action.getBaseMove()) {
                        case BIDE:
                        case COUNTER:
                        case ENDEAVOR:
                        case FINAL_GAMBIT:
                        case GUARDIAN_OF_ALOLA:
                        case METAL_BURST:
                        case MIRROR_COAT:
                        case NATURES_MADNESS:
                        case NIGHT_SHADE:
                        case PSYWAVE:
                        case SEISMIC_TOSS:
                        case SUPER_FANG:
                            return 1.0;
                    }

                    if (action.getCategory() == Category.PHYSICAL) {
                        return 0.5;
                    }

                    return 1.0;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }
    },
    FREEZE("Freeze") {
        @Override
        public void onOwnReceiveAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
            Generation generation = attacker.getBattle().getGeneration();

            switch (generation) {
                case I:
                    if (action.getType() == Type.FIRE && action.getBaseMove() != BaseMove.FIRE_SPIN) {
                        this.remove(defender);
                    }
                    break;
                case II:
                    if (action.getType() == Type.FIRE && action.getBaseMove() != BaseMove.FIRE_SPIN) {
                        this.remove(defender);
                        break;
                    }
                    if (action.getBaseMove() == BaseMove.TRI_ATTACK && Math.random() < 0.3333333333) {
                        this.remove(defender);
                    }
                    break;
                case III:
                    if (action.getType() == Type.FIRE && action.getBaseMove() != BaseMove.HIDDEN_POWER) {
                        this.remove(defender);
                    }
                    break;
                case IV:
                    if (action.getType() == Type.FIRE) {
                        this.remove(defender);
                    }
                case V:
                case VI:
                case VII:
                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }

        @Override
        public void onOwnTurnStart(@Nonnull Pokemon pokemon) {
            Generation generation = pokemon.getBattle().getGeneration();

            switch (generation) {
                case I:
                    break;
                case II:
                    if (Math.random() < 0.1) {
                        pokemon.setCanAttackThisTurn(false);
                        this.remove(pokemon);
                    }
                case III:
                    if (Math.random() < 0.2) {
                        this.remove(pokemon);
                    }
                    break;
                case IV:
                    break;
                case V:
                    break;
                case VI:
                    break;
                case VII:
                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }
    },
    PARALYSIS("Paralysis"),
    POISON("Poison"),
    BADLY_POISONED("Badly poisoned"),
    SLEEP("Sleep") {
        @Override
        public Integer getDuration(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
            if (move.getBaseMove() == BaseMove.REST) {
                return 2;
            }

            switch (battle.getGeneration()) {
                case I:
                    return ThreadLocalRandom.current().nextInt(1, 7 + 1);
                case II:
                case III:
                case IV:
                    return ThreadLocalRandom.current().nextInt(1, 5 + 1);
                case V:
                    return ThreadLocalRandom.current().nextInt(1, 3 + 1);
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }
        }

        @Override
        public boolean hasInfiniteDuration() {
            return false;
        }
    };

    private String NAME;

    Status(@Nonnull String name) {
        this.NAME = name;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nullable
    public Integer getDuration(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
        return null;
    }

    public boolean hasInfiniteDuration() {
        return true;
    }

    @OverridingMethodsMustInvokeSuper
    protected boolean apply(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
        target.setStatus(this);
        return true;
    }

    @OverridingMethodsMustInvokeSuper
    protected void remove(Pokemon user) {
        user.resetStatus();
    }

}
