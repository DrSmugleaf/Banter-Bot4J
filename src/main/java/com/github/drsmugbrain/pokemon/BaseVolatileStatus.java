package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 18/06/2017.
 */
public enum BaseVolatileStatus {

    BOUND("Bound"),
    CANT_ESCAPE("Can't escape"),
    CONFUSION("Confusion"),
    CURSE("Curse"),
    EMBARGO("Embargo"),
    ENCORE("Encore"),
    FLINCH("Flinch"),
    HEAL_BLOCK("Heal Block"),
    IDENTIFIED("Identified"),
    INFATUATION("Infatuation"),
    LEECH_SEED("Leech Seed"),
    NIGHTMARE("Nightmare"),
    PERISH_SONG("Perish Song"),
    SPOOKED("Spooked"),
    TAUNT("Taunt"),
    TELEKINESIS("Telekinesis"),
    TORMENT("Torment"),

    AQUA_RING("Aqua Ring"),
    BRACING("Bracing"),
    CENTER_OF_ATTENTION("Center of attention"),
    DEFENSE_CURL("Defense Curl"),
    GLOWING("Glowing"),
    ROOTING("Rooting"),
    MAGIC_COAT("Magic Coat"),
    MAGNETIC_LEVITATION("Magnetic Levitation"),
    MINIMIZE("Minimize"),
    PROTECTION("Protection"),
    RECHARGING("Recharging"),
    SEMI_INVULNERABLE("Semi-invulnerable"),
    SUBSTITUTE("Substitute"),
    TAKING_AIM("Taking aim"),
    TAKING_IN_SUNLIGHT("Taking in sunlight"),
    WITHDRAWING("Withdrawing"),
    WHIPPING_UP_A_WHIRLWIND("Whipping up a whirlwind"),

    AURORA_VEIL("Aurora Veil", 5) {
        @Override
        protected void apply(Pokemon user, Pokemon target, Battle battle, Trainer trainer, BaseMove move) {
            if (battle.getWeather() != Weather.HAIL) {
                this.fail();
                return;
            }

            super.apply(user, target, battle, trainer, move);
        }

        @Override
        protected double modifyDamage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
            return 0.5;
        }
    },
    BANEFUL_BUNKER("Baneful Bunker", 1) {
        @Override
        protected boolean receiveAttackAttempt(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
            BaseMove baseMove = move.getBaseMove();

            switch (baseMove) {
                case ACUPRESSURE:
                case AROMATIC_MIST:
                case BESTOW:
                case BLOCK:
                case CONFIDE:
                case CONVERSION_2:
                case CURSE:
                case DOOM_DESIRE:
                case FEINT:
                case FUTURE_SIGHT:
                case HYPERSPACE_FURY:
                case HYPERSPACE_HOLE:
                case HOLD_HANDS:
                case MEAN_LOOK:
                case PERISH_SONG:
                case PHANTOM_FORCE:
                case PLAY_NICE:
                case PSYCH_UP:
                case ROAR:
                case ROLE_PLAY:
                case SHADOW_FORCE:
                case SKETCH:
                case SPIDER_WEB:
                case TEARFUL_LOOK:
                case TRANSFORM:
                case WHIRLWIND:
                    return true;
            }

            switch (baseMove.getTarget()) {
                case SELF:
                case ALL_ALLIES:
                case ALL:
                case ALL_FOES:
                    return true;
            }

            return false;
        }

        @Override
        protected double modifyDamage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
            if (move.getBaseMove().isZMove()) {
                return 0.25;
            }

            return 1.0;
        }

        @Override
        protected void onReceiveAttack(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, int damage) {
            BaseMove baseMove = move.getBaseMove();

            switch (baseMove) {
                case FEINT:
                case PHANTOM_FORCE:
                case SHADOW_FORCE:
                case HYPERSPACE_HOLE:
                case HYPERSPACE_FURY:
                    this.breakStatus(target);
                    break;
            }

            if (baseMove.physicalContact()) {
                target.setStatus(Status.POISON);
            }
        }
    },
    BEAK_BLAST("Beak Blast", 1) {
        @Override
        protected void onReceiveAttack(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, int damage) {
            if (move.getBaseMove().physicalContact()) {
                target.setStatus(Status.BURN);
            }
        }
    },
    BIDE("Bide", 2) {
        @Override
        protected void onReceiveAttack(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, int damage) {
            user.addBideDamageTaken(damage);
            user.setBideTarget(user);
        }

        @Override
        protected void onReceiveStatusEffect(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Status status) {

        }
    },
    BIND("Bind") {
        @Override
        protected void apply(Pokemon user, Pokemon target, Battle battle, Trainer trainer, BaseMove move) {
            double random = Math.random();
            if (random < 0.375) {
                this.apply(user, user, battle, trainer, move, 2);
            } else if (random < 0.75) {
                this.apply(user, user, battle, trainer, move, 3);
            } else if (random < 0.875) {
                this.apply(user, user, battle, trainer, move, 4);
            } else if (random < 1) {
                this.apply(user, user, battle, trainer, move, 5);
            }
        }

        @Override
        protected void onDealAttack(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, int damage) {
            target.setCanAttackThisTurn(false);
            super.onDealAttack(user, target, battle, trainer, move, damage);
        }

        @Override
        protected void onTrainerTurnStart(Pokemon pokemon, Trainer trainer, Battle battle) {
            pokemon.setValidMoves(BaseMove.BIND);
            super.onTrainerTurnStart(pokemon, trainer, battle);
        }

        @Override
        protected void onOwnSwitch(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
            if (user.getLastTarget() != null) {
                user.getLastTarget().setCanAttackThisTurn(false);
            }
            super.onOwnSwitch(user, target, battle, trainer);
        }

        @Override
        protected void afterEnemySwitch(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
            target.setCanAttackThisTurn(false);
            super.afterEnemySwitch(user, target, battle, trainer);
        }
    };

    private final String NAME;
    private final Integer DURATION;

    BaseVolatileStatus(@Nonnull String name, @Nullable Integer duration) {
        this.NAME = name;
        this.DURATION = duration;
    }

    BaseVolatileStatus(@Nonnull String name) {
        this(name, 1); // TODO: Volatile statuses durations
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nullable
    public Integer getDuration(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
        return this.DURATION;
    }

    protected void apply(Pokemon user, Pokemon target, Battle battle, Trainer trainer, BaseMove move, int duration) {
        target.addVolatileStatus(new VolatileStatus(this, user, duration));
    }

    protected void apply(Pokemon user, Pokemon target, Battle battle, Trainer trainer, BaseMove move) {
        this.apply(user, target, battle, trainer, move, this.DURATION);
    }

    protected void remove(Pokemon user, Pokemon target, Battle battle, Trainer trainer, BaseMove move) {
        target.removeVolatileStatus(this);
    }

    protected void fail() {}

    protected double modifyDamage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
        return 1.0;
    }

    protected boolean receiveAttackAttempt(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
        return true;
    }

    protected void breakStatus(Pokemon target) {
        target.removeVolatileStatus(this);
    }

    protected void onDealAttack(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, int damage) {}

    protected void onReceiveAttack(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, int damage) {}

    protected void onTrainerTurnStart(Pokemon pokemon, Trainer trainer, Battle battle) {}

    protected void onTurnStart(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {}

    protected void onTurnEnd(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {}

    protected void onTurnEndAfterRecurrentDamage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {}

    protected void onReceiveStatusEffect(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Status status) {}

    protected void onOwnSwitch(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {}

    protected void afterEnemySwitch(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {}

}
