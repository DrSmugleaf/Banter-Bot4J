package com.github.drsmugbrain.pokemon.status;

import com.github.drsmugbrain.pokemon.*;
import com.github.drsmugbrain.pokemon.moves.Action;
import com.github.drsmugbrain.pokemon.moves.BaseMove;
import com.github.drsmugbrain.pokemon.moves.Category;
import com.github.drsmugbrain.pokemon.moves.Move;
import com.github.drsmugbrain.pokemon.stats.IStat;
import com.github.drsmugbrain.pokemon.stats.PermanentStat;
import com.github.drsmugbrain.pokemon.types.Type;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 08/06/2017.
 */
public enum Status implements IStatus, IModifier {

    BURN("Burn") {
        @Override
        public void apply(@Nonnull Pokemon pokemon, @Nonnull Action action) {
            if (pokemon.getBattle().getGeneration() == Generation.I) {
                pokemon.addStatModifier(PermanentStat.ATTACK, BURN, 0.5);
            }

            super.apply(pokemon, action);
        }

        @Override
        public void remove(@Nonnull Pokemon pokemon) {
            if (pokemon.getBattle().getGeneration() == Generation.I) {
                pokemon.removeStatModifier(BURN);
            }

            super.remove(pokemon);
        }

        @Override
        public void onOwnTurnEnd(@Nonnull Pokemon pokemon) {
            Generation generation = pokemon.getBattle().getGeneration();

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
        public double damageMultiplier(@Nonnull Pokemon attacker, @Nonnull Action action) {
            Generation generation = attacker.getBattle().getGeneration();

            switch (generation) {
                case I:
                case II:
                    return 1.0;
                case III:
                case IV:
                case V:
                case VI:
                case VII:
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
        public boolean onOwnReceiveAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
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
        public boolean onOwnAttemptAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
            Generation generation = attacker.getBattle().getGeneration();

            switch (generation) {
                case I:
                    return true;
                case II:
                    if (Math.random() < 0.1) {
                        this.remove(attacker);
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
                        this.remove(attacker);
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
        public void apply(@Nonnull Pokemon pokemon, @Nonnull Action action) {
            if (pokemon.getBattle().getGeneration() == Generation.I) {
                pokemon.addStatModifier(PermanentStat.SPEED, PARALYSIS, 0.5);
            }

            super.apply(pokemon, action);
        }

        @Override
        public void remove(@Nonnull Pokemon pokemon) {
            if (pokemon.getBattle().getGeneration() == Generation.I) {
                pokemon.removeStatModifier(PARALYSIS);
            }

            super.remove(pokemon);
        }

        @Override
        public boolean onOwnAttemptAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
            if (Math.random() < 0.25) {
                return false;
            }

            return true;
        }

        @Override
        public double statMultiplier(@Nonnull Pokemon pokemon, @Nonnull IStat stat) {
            Generation generation = pokemon.getBattle().getGeneration();

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
        public void onOwnTurnEnd(@Nonnull Pokemon pokemon) {
            Generation generation = pokemon.getBattle().getGeneration();

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
        public void remove(@Nonnull Pokemon user) {
            user.resetToxicN();
            super.remove(user);
        }

        @Override
        public void onOwnTurnEnd(@Nonnull Pokemon pokemon) {
            Generation generation = pokemon.getBattle().getGeneration();

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
        public void onOwnSendBack(@Nonnull Pokemon pokemon) {
            Generation generation = pokemon.getBattle().getGeneration();

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
            switch (battle.getGeneration()) {
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
                    throw new InvalidGenerationException(battle.getGeneration());
            }
        }

        @Override
        public boolean onOwnAttemptAttack(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action) {
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

    @Override
    public void apply(@Nonnull Pokemon pokemon, @Nonnull Action action) {
        pokemon.STATUSES.setStatus(this);
    }

    @Override
    public void remove(@Nonnull Pokemon pokemon) {
        pokemon.STATUSES.resetStatus();
    }

}
