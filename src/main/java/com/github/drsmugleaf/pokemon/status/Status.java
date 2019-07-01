package com.github.drsmugleaf.pokemon.status;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.battle.*;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.moves.Move;
import com.github.drsmugleaf.pokemon.moves.MoveCategory;
import com.github.drsmugleaf.pokemon.pokemon.Pokemon;
import com.github.drsmugleaf.pokemon.stats.IStat;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon.trainer.Trainer;
import com.github.drsmugleaf.pokemon.types.Type;
import org.jetbrains.annotations.Contract;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 08/06/2017.
 */
public enum Status implements IStatus, IModifier {

    BURN("Burn") {
        @Override
        public void apply(Pokemon pokemon, Action action) {
            if (pokemon.getBattle().GENERATION == Generation.I) {
                pokemon.addStatModifier(PermanentStat.ATTACK, BURN, 0.5);
            }

            super.apply(pokemon, action);
        }

        @Override
        public void remove(Pokemon pokemon) {
            if (pokemon.getBattle().GENERATION == Generation.I) {
                pokemon.removeStatModifier(BURN);
            }

            super.remove(pokemon);
        }

        @Override
        public void onOwnTurnEnd(Pokemon pokemon) {
            Generation generation = pokemon.getBattle().GENERATION;

            switch (generation) {
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
                    throw new InvalidGenerationException(generation);
            }
        }

        @Override
        public double damageMultiplier(Action action) {
            Generation generation = action.getGeneration();

            switch (generation) {
                case I:
                case II:
                    return 1.0;
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    switch (action.BASE_MOVE) {
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

                    if (action.getCategory() == MoveCategory.PHYSICAL) {
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
        public boolean onOwnReceiveAttack(Pokemon attacker, Pokemon defender, Action action) {
            Generation generation = attacker.getBattle().GENERATION;

            switch (generation) {
                case I:
                    if (action.getType() == Type.FIRE && action.BASE_MOVE != BaseMove.FIRE_SPIN) {
                        remove(defender);
                    }
                    break;
                case II:
                    if (action.getType() == Type.FIRE && action.BASE_MOVE != BaseMove.FIRE_SPIN) {
                        remove(defender);
                        break;
                    }
                    if (action.BASE_MOVE == BaseMove.TRI_ATTACK && Math.random() < 0.3333333333) {
                        remove(defender);
                    }
                    break;
                case III:
                    if (action.getType() == Type.FIRE && action.BASE_MOVE != BaseMove.HIDDEN_POWER) {
                        remove(defender);
                    }
                    break;
                case IV:
                    if (action.getType() == Type.FIRE) {
                        remove(defender);
                    }
                    break;
                case V:
                case VI:
                case VII:
                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }

            return true;
        }

        @Override
        public boolean onOwnAttemptAttack(Pokemon attacker, Pokemon defender, Action action) {
            Generation generation = attacker.getBattle().GENERATION;

            switch (generation) {
                case I:
                    return true;
                case II:
                    if (Math.random() < 0.1) {
                        remove(attacker);
                        return false;
                    } else {
                        return false;
                    }
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    if (Math.random() < 0.2) {
                        remove(attacker);
                        return true;
                    } else {
                        return false;
                    }
                default:
                    throw new InvalidGenerationException(generation);
            }
        }
    },
    PARALYSIS("Paralysis") {
        @Override
        public void apply(Pokemon pokemon, Action action) {
            if (pokemon.getBattle().GENERATION == Generation.I) {
                pokemon.addStatModifier(PermanentStat.SPEED, PARALYSIS, 0.5);
            }

            if (action.getGeneration().isAfter(Generation.V) && pokemon.TYPES.isType(Type.ELECTRIC)) {
                fail();
                return;
            }

            super.apply(pokemon, action);
        }

        @Override
        public void remove(Pokemon pokemon) {
            if (pokemon.getBattle().GENERATION == Generation.I) {
                pokemon.removeStatModifier(PARALYSIS);
            }

            super.remove(pokemon);
        }

        @Override
        public boolean onOwnAttemptAttack(Pokemon attacker, Pokemon defender, Action action) {
            return !(Math.random() < 0.25);
        }

        @Override
        public double statMultiplier(Pokemon pokemon, IStat stat) {
            Generation generation = pokemon.getBattle().GENERATION;

            if (stat == PermanentStat.SPEED) {
                switch (generation) {
                    case I:
                        break;
                    case II:
                    case III:
                    case IV:
                    case V:
                    case VI:
                        return 0.25;
                    case VII:
                        return 0.5;
                    default:
                        throw new InvalidGenerationException(generation);
                }
            }

            return 1.0;
        }
    },
    POISON("Poison") {
        @Override
        public void onOwnTurnEnd(Pokemon pokemon) {
            Generation generation = pokemon.getBattle().GENERATION;

            switch (generation) {
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
                    throw new InvalidGenerationException(generation);
            }
        }
    },
    BADLY_POISONED("Badly poisoned") {
        @Override
        public void remove(Pokemon user) {
            user.resetToxicN();
            super.remove(user);
        }

        @Override
        public void onOwnTurnEnd(Pokemon pokemon) {
            Generation generation = pokemon.getBattle().GENERATION;

            switch (generation) {
                case I:
                    pokemon.damage(6.25 * pokemon.getToxicN());
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                    pokemon.damage(6.25 * pokemon.getToxicN());
                    break;
                case VII:
                    pokemon.damage(6.25 * pokemon.getToxicN());
                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }

            pokemon.increaseToxicN();
        }

        @Override
        public void onOwnSendBack(Pokemon pokemon) {
            Generation generation = pokemon.getBattle().GENERATION;

            switch (generation) {
                case I:
                    break;
                case II:
                    break;
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    pokemon.resetToxicN();
                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }
        }
    },
    SLEEP("Sleep") {
        @Override
        public Integer getDuration(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
            switch (battle.GENERATION) {
                case I:
                    return ThreadLocalRandom.current().nextInt(1, 7 + 1);
                case II:
                    return ThreadLocalRandom.current().nextInt(2, 7 + 1);
                case III:
                case IV:
                    return ThreadLocalRandom.current().nextInt(2, 5 + 1);
                case V:
                case VI:
                case VII:
                    return ThreadLocalRandom.current().nextInt(1, 3 + 1);
                default:
                    throw new InvalidGenerationException(battle.GENERATION);
            }
        }

        @Override
        public boolean onOwnAttemptAttack(Pokemon attacker, Pokemon defender, Action action) {
            return false;
        }
    };

    public String NAME;

    Status(String name) {
        NAME = name;
    }

    @Contract(pure = true)
    public String getName() {
        return NAME;
    }

    @Nullable
    public Integer getDuration(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move) {
        return null;
    }

    @Override
    public void apply(Pokemon pokemon, Action action) {
        pokemon.STATUSES.setStatus(this);
    }

    protected void fail() {}

    @Override
    public void remove(Pokemon pokemon) {
        pokemon.STATUSES.resetStatus();
    }

}
