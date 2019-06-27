package com.github.drsmugleaf.pokemon.status;

import com.github.drsmugleaf.pokemon.ability.Abilities;
import com.github.drsmugleaf.pokemon.battle.*;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.moves.MoveCategory;
import com.github.drsmugleaf.pokemon.moves.MoveEffect;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import com.github.drsmugleaf.pokemon.types.Type;
import org.jetbrains.annotations.Contract;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 18/06/2017.
 */
public enum BaseVolatileStatus implements IStatus, IModifier {

    BOUND("Bound") {
        @Override
        public Integer getDuration(Action action) {
            double random = Math.random();
            Integer duration;

            Generation generation = action.getAttacker().getBattle().GENERATION;
            switch (generation) {
                case I:
                case II:
                case III:
                case IV:
                    if (random < 0.375) {
                        duration = 2;
                    } else if (random < 0.75) {
                        duration = 3;
                    } else if (random < 0.875) {
                        duration = 4;
                    } else {
                        duration = 5;
                    }

                    break;
                case V:
                case VI:
                case VII:
                    if (random < 0.5) {
                        duration = 4;
                    } else {
                        duration = 5;
                    }

                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }

            return duration;
        }

        @Override
        public void onTrainerTurnStart(Trainer trainer, Pokemon pokemon) {
            pokemon.STATUSES.getVolatileStatus(this).ACTION.getAttacker().MOVES.setValid(BaseMove.BIND);
        }

        @Override
        public void onEnemySendBack(Pokemon user, Pokemon enemy) {
            Generation generation = user.getBattle().GENERATION;

            if (generation != Generation.I) {
                Action action = user.STATUSES.getVolatileStatus(this).ACTION;
                Pokemon applier = action.getAttacker();
                if (applier == enemy) {
                    remove(user);
                }
            }
        }

        @Override
        public boolean onOwnAttemptAttack(Pokemon attacker, Pokemon defender, Action action) {
            Generation generation = attacker.getBattle().GENERATION;

            switch (generation) {
                case I:
                    return false;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    return true;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }

        @Override
        public boolean canSwitch(Pokemon pokemon) {
            Generation generation = pokemon.getBattle().GENERATION;

            switch (generation) {
                case I:
                    return true;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    return false;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }
    },
    CANT_ESCAPE("Can't escape"),
    CONFUSION("Confusion") {
        @Override
        public Integer getDuration(Action action) {
            return ThreadLocalRandom.current().nextInt(1, 4 + 1);
        }
    },
    CURSE("Curse") {
        @Override
        public void onTurnEnd(Battle battle, Pokemon pokemon) {
            pokemon.damage(25.0);
        }
    },
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
    MAGIC_COAT("Magic Coat") {
        @Override
        public boolean onOwnReceiveAttack(Pokemon attacker, Pokemon defender, Action action) {
            Generation generation = attacker.getBattle().GENERATION;

            switch (generation) {
                case I:
                    break;
                case II:
                    break;
                case III:
                    break;
                case IV:
                    break;
                case V:
                    if (action.BASE_MOVE == BaseMove.DEFOG) {
                        action.reflect(defender);
                        return false;
                    }
                    break;
                case VI:
                    break;
                case VII:
                    break;
            }

            return true;
        }
    },
    MAGNETIC_LEVITATION("Magnetic Levitation"),
    MINIMIZE("Minimize"),
    PROTECTION("Protection"),
    RECHARGING("Recharging"),
    SEMI_INVULNERABLE("Semi-invulnerable", 1) {
        @Override
        public void onTrainerTurnStart(Trainer trainer, Pokemon pokemon) {
            VolatileStatus status = pokemon.STATUSES.getVolatileStatus(this);
            if (status == null) {
                throw new IllegalStateException("Semi-invulnerable status not found on defending pokemon");
            }

            BaseMove statusMove = status.ACTION.BASE_MOVE;

            pokemon.MOVES.setValid(statusMove);
        }

        @Override
        public boolean onOwnReceiveAttack(Pokemon attacker, Pokemon defender, Action action) {
            VolatileStatus status = defender.STATUSES.getVolatileStatus(this);
            if (status == null) {
                throw new IllegalStateException("Semi-invulnerable status not found on defending pokemon");
            }

            if (defender.STATUSES.hasVolatileStatus(TAKING_AIM)) {
                return true;
            }

            if (defender.ABILITY.get() == Abilities.NO_GUARD || attacker.ABILITY.get() == Abilities.NO_GUARD) {
                return true;
            }

            BaseMove move = action.BASE_MOVE;
            BaseMove statusMove = status.ACTION.BASE_MOVE;

            if (action.getGeneration() == Generation.I) {
                switch (move) {
                    case SWIFT:
                    case TRANSFORM:
                    case BIDE:
                        return true;
                }
            }

            switch (statusMove) {
                case FLY:
                case BOUNCE:
                case SKY_DROP:
                    switch (move) {
                        case GUST:
                        case SMACK_DOWN:
                        case SKY_UPPERCUT:
                        case THUNDER:
                        case TWISTER:
                        case HURRICANE:
                        case GRAVITY:
                            return true;
                        default:
                            return false;
                    }
                case DIG:
                    switch (move) {
                        case EARTHQUAKE:
                        case MAGNITUDE:
                        case FISSURE:
                            return true;
                        default:
                            return false;
                    }
                case DIVE:
                    switch (move) {
                        case SURF:
                        case WHIRLPOOL:
                            return true;
                        default:
                            return false;
                    }
                case SHADOW_FORCE:
                case PHANTOM_FORCE:
                    return false;
            }

            return false;
        }
    },
    SUBSTITUTE("Substitute"),
    TAKING_AIM("Taking aim"),
    TAKING_IN_SUNLIGHT("Taking in sunlight"),
    WITHDRAWING("Withdrawing"),
    WHIPPING_UP_A_WHIRLWIND("Whipping up a whirlwind"),
    AURORA_VEIL("Aurora Veil", 5) {
        @Override
        public void apply(Pokemon user, Action action) {
            if (user.getBattle().getWeather() != Weather.HAIL) {
                fail();
                return;
            }

            super.apply(user, action);
        }

        @Override
        public double enemyDamageMultiplier(Pokemon pokemon, Action action) {
            if (action.getCategory() == MoveCategory.PHYSICAL || action.getCategory() == MoveCategory.SPECIAL) {
                if (action.getBattle().VARIATION == Variation.SINGLE_BATTLE) {
                    return 0.5;
                } else {
                    return 0.333;
                }
            }

            return 0.5;
        }
    },
    BANEFUL_BUNKER("Baneful Bunker", 1) {
        @Override
        public boolean onEnemyAttemptAttack(Pokemon attacker, Pokemon defender, Action action) {
            BaseMove baseMove = action.BASE_MOVE;

            switch (baseMove) {
                case FEINT:
                case PHANTOM_FORCE:
                case SHADOW_FORCE:
                case HYPERSPACE_HOLE:
                case HYPERSPACE_FURY:
                    breakStatus(defender);
                case ACUPRESSURE:
                case AROMATIC_MIST:
                case BESTOW:
                case BLOCK:
                case CONFIDE:
                case CONVERSION_2:
                case CURSE:
                case DOOM_DESIRE:
                case FUTURE_SIGHT:
                case HOLD_HANDS:
                case MEAN_LOOK:
                case PERISH_SONG:
                case PLAY_NICE:
                case PSYCH_UP:
                case ROAR:
                case ROLE_PLAY:
                case SKETCH:
                case SPIDER_WEB:
                case TEARFUL_LOOK:
                case TRANSFORM:
                case WHIRLWIND:
                    return true;
            }

            switch (baseMove.TARGET) {
                case SELF:
                case ALL_ALLIES:
                case ALL:
                case ALL_FOES:
                    return true;
            }

            if (baseMove.PHYSICAL_CONTACT) {
                attacker.STATUSES.setStatus(Status.POISON);
            }

            return false;
        }

        @Override
        public double damageMultiplier(Action action) {
            if (action.BASE_MOVE.IS_Z_MOVE) {
                return 0.25;
            }

            return 1.0;
        }
    },
    BEAK_BLAST("Beak Blast", 1) {
        @Override
        public boolean onOwnReceiveAttack(Pokemon attacker, Pokemon defender, Action action) {
            if (action.BASE_MOVE.PHYSICAL_CONTACT) {
                attacker.STATUSES.setStatus(Status.BURN);
            }

            return true;
        }
    },
    BIDE("Bide", 2) {
        @Override
        public boolean onOwnReceiveAttack(Pokemon attacker, Pokemon defender, Action action) {
//            defender.addBideDamageTaken(action.DAMAGE);
//            defender.setBideTarget(attacker);
            // TODO: Bide damage taken processing
            return true;
        }
    },
    STOCKPILE_1("Stockpile 1"),
    STOCKPILE_2("Stockpile 2"),
    STOCKPILE_3("Stockpile 3"),
    PERISH_0("Perish 0"),
    PERISH_1("Perish 1"),
    PERISH_2("Perish 2"),
    PERISH_3("Perish 3"),
    UPROAR("Uproar"),
    BOUNCE("Bounce", 1) {
        @Override
        public void onTrainerTurnStart(Trainer trainer, Pokemon pokemon) {
            pokemon.MOVES.setValid(BaseMove.BOUNCE);
        }

        @Override
        public void remove(Pokemon user) {
            super.remove(user);
        }
    },
    LIGHT_SCREEN("Light Screen"),
    REFLECT("Reflect"),
    CHARGE("Charge", 2) {
        @Override
        public double damageMultiplier(Action action) {
            Generation generation = action.getGeneration();

            switch (generation) {
                case I:
                case II:
                case III:
                    if (action.getType() == Type.ELECTRIC) {
                        return 2.0;
                    }
                    return 1.0;
                case IV:
                case V:
                case VI:
                case VII:
                    return 1.0;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }

        @Override
        public double powerMultiplier(Pokemon attacker, Action action) {
            Generation generation = attacker.getBattle().GENERATION;

            switch (generation) {
                case I:
                case II:
                case III:
                    return 1.0;
                case IV:
                case V:
                case VI:
                case VII:
                    if (action.getType() == Type.ELECTRIC) {
                        return 2.0;
                    }
                    return 1.0;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }
    },
    CRAFTY_SHIELD("Crafty Shield", 0) {
        @Override
        public boolean onOwnReceiveAttack(Pokemon attacker, Pokemon defender, Action action) {
            return action.getCategory() != MoveCategory.OTHER
                   || attacker == defender
                   || action.BASE_MOVE == BaseMove.PERISH_SONG
                   || action.BASE_MOVE.EFFECT == MoveEffect.ENTRY_HAZARD;
        }
    },
    MIST("Mist", Integer.MAX_VALUE),
    SAFEGUARD("Safeguard", 5);

    public final String NAME;
    private final Integer DURATION;

    BaseVolatileStatus(String name, Integer duration) {
        NAME = name;
        DURATION = duration;
    }

    BaseVolatileStatus(String name) {
        this(name, 1); // TODO: Volatile statuses durations
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

    @Contract(pure = true)
    public Integer getDuration(Action action) {
        return DURATION;
    }

    @Override
    public void apply(Pokemon pokemon, Action action) {
        int duration = getDuration(action);
        VolatileStatus status = new VolatileStatus(this, action, duration);
        pokemon.STATUSES.addVolatileStatus(status);
    }

    @Override
    public void remove(Pokemon pokemon) {
        pokemon.STATUSES.removeVolatileStatus(this);
    }

    protected void fail() {}

    protected void breakStatus(Pokemon target) {
        target.STATUSES.removeVolatileStatus(this);
    }

    protected void onTurnStart(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {}

}
