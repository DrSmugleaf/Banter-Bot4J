package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 08/06/2017.
 */
public enum Status {

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
        protected void remove(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
            switch (battle.getGeneration()) {
                case I:
                case II:
                    user.removeStatModifier(Stat.ATTACK, "burn");
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
            super.remove(user, target, battle, trainer, move);
        }

        @Override
        protected void onTurnEnd(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
            switch (battle.getGeneration()) {
                case I:
                    user.damage(6.25);
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                    user.damage(12.5);
                    break;
                case VII:
                    user.damage(6.25);
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }
            user.damage(6.25);
        }

        @Override
        protected double getDamageDone(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
            switch (battle.getGeneration()) {
                case I:
                case II:
                    return 1.0;
                case III:
                case IV:
                case V:
                    switch (move.getBaseMove()) {
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

                    if (move.getCategory() == Category.PHYSICAL) {
                        return 0.5;
                    }

                    return 1.0;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }
        }
    },
    FREEZE("Freeze") {
        @Override
        protected void onReceiveAttack(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
            switch (battle.getGeneration()) {
                case I:
                    if (move.getType() == Type.FIRE && move.getBaseMove() != BaseMove.FIRE_SPIN) {
                        this.remove(user, target, battle, trainer, move);
                    }
                    break;
                case II:
                    if (move.getType() == Type.FIRE && move.getBaseMove() != BaseMove.FIRE_SPIN) {
                        this.remove(user, target, battle, trainer, move);
                        break;
                    }
                    if (move.getBaseMove() == BaseMove.TRI_ATTACK && Math.random() < 0.3333333333) {
                        this.remove(user, target, battle, trainer, move);
                    }
                    break;
                case III:
                    if (move.getType() == Type.FIRE && move.getBaseMove() != BaseMove.HIDDEN_POWER) {
                        this.remove(user, target, battle, trainer, move);
                    }
                    break;
                case IV:
                    if (move.getType() == Type.FIRE) {
                        this.remove(user, target, battle, trainer, move);
                    }
                case V:
                case VI:
                case VII:
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            super.onReceiveAttack(user, target, battle, trainer, move);
        }

        @Override
        protected void onOwnTurnStart(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
            switch (battle.getGeneration()) {
                case I:
                    break;
                case II:
                    if (Math.random() < 0.1) {
                        user.setCanAttackThisTurn(false);
                        this.remove(user, target, battle, trainer, move);
                    }
                case III:
                    if (Math.random() < 0.2) {
                        this.remove(user, target, battle, trainer, move);
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
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            super.onOwnTurnStart(user, target, battle, trainer, move);
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

    protected boolean apply(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
        target.setStatus(this);
        return true;
    }

    protected void remove(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
        this.remove(user, target, battle, trainer, move.getBaseMove());
    }

    protected void remove(Pokemon user, Pokemon target, Battle battle, Trainer trainer, BaseMove move) {
        target.resetStatus();
    }

    protected void onTurnStart(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {}

    protected void onTurnEnd(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {}

    protected double getDamageDone(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
        return 1.0;
    }

    protected double getDamageReceived(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
        return 1.0;
    }

    protected void onReceiveAttack(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {}

    protected void onOwnTurnStart(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {}

}
