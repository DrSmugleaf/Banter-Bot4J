package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 21/07/2017.
 */
public enum Messages {

    STEALTH_ROCK("Stealth Rock", "Pointed stones dug into %s!"),
    SPIKES("Spikes", "%s is hurt by the spikes!"),
    BURN("Burn", "%s was hurt by its burn!"),
    POISON("Poison", "%s was hurt by poison!"),
    LIFE_ORB("Life Orb", "%s lost some of its HP!"),
    RECOIL("Recoil", "%s is damaged by the recoil!"),
    SANDSTORM("Sandstorm", "%s is buffeted by the sandstorm!"),
    HAIL("Hail", "%s is buffeted by the hail!"),
    BAD_DREAMS("Bad Dreams", "%s is tormented!"),
    CURSE("Curse", "%s is afflicted by the curse!"),
    NIGHTMARE("Nightmare", "%s is locked in a nightmare!"),
    SPIKY_SHIELD("Spiky Shield", "%s was hurt!"),
    AFTERMATH("Aftermath", "%s is hurt!"),
    LIQUID_OOZE("Liquid Ooze", "%s sucked up the liquid ooze!"),
    CONFUSION("Confusion", "It hurt itself in its confusion!"),
    LEECH_SEED("Leech Seed", "%s's health is sapped by Leech Seed!"),
    FLAME_BURST("Flame Burst", "The bursting flame hit %s!"),
    FIRE_PLEDGE("Fire Pledge", "%s is hurt by the sea of fire!"),
    JUMP_KICK("Jump Kick", "%s kept going and crashed!"),
    HIGH_JUMP_KICK("High Jump Kick", "%s kept going and crashed!"),
    BIND("Bind", "%s is hurt by Bind!"),
    WRAP("Wrap", "%s is hurt by Wrap!"),

    MEMENTO("Memento", "%s's HP was restored by the Z-Power!"),
    PARTING_SHOT("Parting Shot", "%s's HP was restored by the Z-Power!"),
    INGRAIN("Ingrain", "%s absorbed nutrients with its roots!"),
    AQUA_RING("Aqua Ring", "A veil of water restored %s's HP!"),
    HEALING_WISH("Healing Wish", "The healing wish came true for %s!"),
    LUNAR_DANCE("Lunar Dance", "%s became cloaked in mystical moonlight!"),
    WISH("Wish", "%s's wish came true!"),
    DRAIN("Drain", "%s had its energy drained!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    LEFTOVERS("Leftovers", "%s restored a little HP using its %s!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), item.getName());
        }
    },
    SHELL_BELL("Shell Bell", "%s restored a little HP using its %s!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), item.getName());
        }
    },
    BLACK_SLUDGE("Black Sludge", "%s restored a little HP using its %s!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), item.getName());
        }
    },

    HEAL_Z_MOVE("Heal Z-Move", "%s restored its HP using its Z-Power!"),
    HEAL("Heal", "%s restored its HP."),

    PAIN_SPLIT("Pain Split", "The battlers shared their pain!"),

    STAT_BOOST_FAIL("Stat Boost Fail", "%s's %s won't go any higher!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), stat[0].getName());
        }
    },
    STAT_BOOST_1("Stat Boost 1", "%s's %s rose!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), stat[0].getName());
        }
    },
    STAT_BOOST_2("Stat Boost 2", "%s's %s rose sharply!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), stat[0].getName());
        }
    },
    STAT_BOOST_3("Stat Boost 3", "%s's %s rose dramatically!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), stat[0].getName());
        }
    },
    STAT_BOOST_ITEM_1("Stat Boost Item 1", "The %s raised %s's %s!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, item.getName(), user.getNickname(), stat[0].getName());
        }
    },
    STAT_BOOST_ITEM_2("Stat Boost Item 2", "The %s sharply raised %s's %s!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, item.getName(), user.getNickname(), stat[0].getName());
        }
    },
    STAT_BOOST_ITEM_3("Stat Boost Item 3", "The %s dramatically raised %s's %s!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, item.getName(), user.getNickname(), stat[0].getName());
        }
    },
    STAT_BOOST_Z_MOVE_1("Stat Boost Z-Move 1", "%s boosted its stats using its Z-Power!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname());
        }
    },
    STAT_BOOST_Z_MOVE_2("Stat Boost Z-Move 2", "%s boosted its stats sharply using its Z-Power!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname());
        }
    },
    STAT_BOOST_Z_MOVE_3("Stat Boost Z-Move 3", "%s boosted its stats dramatically using its Z-Power!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname());
        }
    },

    STAT_LOSS_FAIL("Stat Loss Fail", "%s's %s won't go any lower!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), stat[0].getName());
        }
    },
    STAT_LOSS_1("Stat Loss 1", "%s's %s fell!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), stat[0].getName());
        }
    },
    STAT_LOSS_2("Stat Loss 2", "%s's %s fell harshly!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), stat[0].getName());
        }
    },
    STAT_LOSS_3("Stat Loss 3", "%s's %s fell severely!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), stat[0].getName());
        }
    },
    STAT_LOSS_ITEM_1("Stat Loss Item 1", "The %s lowered %s's %s!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, item.getName(), user.getNickname(), stat[0].getName());
        }
    },
    STAT_LOSS_ITEM_2("Stat Loss Item 2", "The %s harshly lowered %s's %s!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, item.getName(), user.getNickname(), stat[0].getName());
        }
    },
    STAT_LOSS_ITEM_3("Stat Loss Item 3", "The %s harshly lowered %s's %s!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, item.getName(), user.getNickname(), stat[0].getName());
        }
    },

    BELLY_DRUM("Belly Drum", "%s cut its own HP and maximized its Attack!"),
    ANGER_POINT("Anger Point", "%s maxed its Attack!"),

    GUARD_SWAP("Guard Swap", "%s switched all changes to its Defense and Sp. Def with its target!"),
    HEART_SWAP("Heart Swap", "%s switched stat changes with its target!"),
    POWER_SWAP("Power Swap", "%s switched all changes to its Attack and Sp. Atk with its target!"),

    SPECTRAL_THIEF("Spectral Thief", "%s stole the target's boosted stats!"),

    CLEAR_NEGATIVE_BOOST("Clear Negative Boost", "%s returned its decreased stats to normal using its Z-Power!"),

    COPY_BOOST("Copy Boost", "%s copied %s's stat changes!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), target.getNickname());
        }
    },

    CLEAR_BOOST("Clear Boost", "%s's stat changes were removed!"),

    INVERT_BOOST("Invert Boost", "%s's stat changes were inverted!"),

    CLEAR_ALL_BOOST("Clear All Boost", "All stat changes were eliminated!"),

    CRITICAL_HIT("Critical Hit", "A critical hit!"),
    CRITICAL_HIT_SPREAD("Critical Hit Spread", "A critical hit on %s!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },

    SUPER_EFFECTIVE("Super Effective", "It's super effective!"),
    SUPER_EFFECTIVE_SPREAD("Super Effective Spread", "It's super effective on %s!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },

    RESISTED("Resisted", "It's not very effective..."),
    RESISTED_SPREAD("Resisted Spread", "It's not very effective on %s.") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },

    IMMUNE("Immune", "It doesn't affect %s...") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    IMMUNE_CONFUSION("Immune Confusion", "%s doesn't become confused!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    IMMUNE_OHKO("Immune OHKO", "%s is unaffected!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    IMMUNE_NO_EFFECT("Immune No Effect", "It had no effect!"),

    MISS_TARGET("Miss Target", "%s avoided the attack!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    MISS("Miss", "%s's attack missed!"),

    FAIL_SELF_MOVE_BECAUSE_SKY_DROP("Fail Self Move Because Sky Drop", "Sky Drop won't let %s go!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    FAIL_BURN("Fail Burn", "%s already has a burn.") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    FAIL_TOXIC("Fail Toxic", "%s is already poisoned.") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    FAIL_POISON("Fail Poison", "%s is already poisoned.") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    FAIL_SLEEP_UPROAR_SELF("Fail Sleep Uproar Self", "But %s can't sleep in an uproar!"),
    FAIL_SLEEP_UPROAR_TARGET("Fail Sleep Uproar Target", "But the uproar kept %s awake!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    FAIL_SLEEP("Fail Sleep", "%s is already asleep!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    FAIL_PARALYZED("Fail Paralyzed", "%s is already paralyzed.") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    FAIL_FREEZE("Fail Freeze", "%s is already frozen solid.") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    FAIL_DARK_VOID_FORME("Fail Dark Void Forme", "But %s can't use it the way it is now!"),
    FAIL_HYPER_SPACE_FURY_FORME("Fail Hyper Space Fury Forme", "But %s can't use the move!"),
    FAIL_MAGIKARPS_REVENGE("Fail Magikarp's Revenge", "But %s can't use the move!"),
    FAIL_SUBSTITUTE_WEAK("Fail Substitute Weak", "But it does not have enough HP left to make a subtitute!"),
    FAIL_SUBSTITUTE("Fail Substitute", "%s already has a substitute!"),
    FAIL_SKY_DROP_HEAVY("Fail Sky Drop Heavy", "%s is too heavy to be lifted!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, target.getNickname());
        }
    },
    FAIL_SKY_DROP("Fail Sky Drop", "But it failed!"),
    FAIL_DESOLATE_LAND("Fail Desolate Land", "The extremely harsh sunlight was not lessened at all!"),
    FAIL_PRIMORDIAL_SEA("Fail Primordial Sea", "There is no relief from this heavy rain!"),
    FAIL_DELTA_STREAM("Fail Delta Stream", "The mysterious strong winds blow on regardless!"),
    FAIL_WEATHER("Fail Weather", "But it failed!"),
    FAIL_STAT_LOSS_FLOWER_VEIL("Fail Stat Loss Flower Veil", "%s surrounded itself with a veil of petals!"),
    FAIL_STAT_LOSS("Fail Stat Loss", "%s's %s was not lowered!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            if (stat.length == 1) {
                return String.format("%s's %s was not lowered!", target.getNickname(), stat[0].getName());
            } else {
                return String.format("%s's stats were not lowered!", target.getNickname());
            }
        }
    },
    FAIL_WATER_TYPE_DESOLATE_LAND("Fail Water Type Desolate Land", "The Water-type attack evaporated in the harsh sunlight!"),
    FAIL_FIRE_TYPE_PRIMORDIAL_SEA("Fail Fire Type Primordial Sea", "The Fire-type attack fizzled out in the heavy rain!"),
    FAIL("Fail", "But it failed!"),

    NO_TARGET("No Target", "But it failed!") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            switch (battle.getGeneration()) {
                case I:
                case II:
                case III:
                case IV:
                    return "But there was no target...";
                case V:
                case VI:
                case VII:
                default:
                    return "But it failed!";
            }
        }
    },

    OHKO("OHKO", "It's a one-hit KO!"),

    HIT_COUNT("Hit Count", "Hit 1 time!") {
        @Nullable
        @Override
        public String getMessage(Integer hitCount) {
            if (hitCount == 1) {
                return "Hit 1 time!";
            } else {
                return String.format("Hit %d times!", hitCount);
            }
        }
    },

    NOTHING("Nothing", "But nothing happened!"),

    WAITING("Waiting", "%s is waiting for %s's move...") {
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format(this.MESSAGE, user.getNickname(), target.getNickname());
        }
    },

    COMBINE("Combine", "The two moves have become one! It's a combined move!"),

    Z_POWER("Z-Power", "%s surrounded itself with its Z-Power!"),

//    PREPARE(),

//    MUST_RECHARGE(),

    STATUS("Status", "%s %s") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, @Nullable Item item, Status status, @Nullable Move move, @Nullable Ability ability) {
            String message = this.MESSAGE;
            if (item != null) {
                message = message.concat(" by the " + item.getName() + "!");
            } else {
                message = message.concat("!");
            }

            switch (status) {
                case BURN:
                    return String.format(message, target.getNickname(), "was burned");
                case FREEZE:
                    return String.format(message, target.getNickname(), "was frozen solid");
                case PARALYSIS:
                    return String.format(message, target.getNickname(), "is paralyzed! It may be unable to move");
                case POISON:
                    return String.format(message, target.getNickname(), "was poisoned");
                case BADLY_POISONED:
                    return String.format(message, target.getNickname(), "was badly poisoned");
                case SLEEP:
                    if (user == target) {
                        return String.format(message, target.getNickname(), "slept and became healthy");
                    } else {
                        return String.format(message, target.getNickname(), "fell asleep");
                    }
                default:
                    return null;
            }
        }
    },

    CURE_STATUS("Cure Status") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target,@Nullable Item item, Status status,@Nullable Move move, @Nullable Ability ability) {
            if (move != null) {
                switch (move.getBaseMove()) {
                    case PSYCHO_SHIFT:
                        return String.format("%s moved its status onto %s!", user.getNickname(), target.getNickname());
                    case FLAME_WHEEL:
                    case FLARE_BLITZ:
                    case FUSION_FLARE:
                    case SACRED_FIRE:
                    case SCALD:
                    case STEAM_ERUPTION:
                        return String.format("%s's %s melted the ice!", user.getNickname(), move.getBaseMove().getName());
                    default:
                        return String.format("%s's %s heals its status!", user.getNickname(), move.getBaseMove().getName());
                }
            }

            if (ability != null) {
                switch (ability) {
                    case NATURAL_CURE:
                        return String.format("(%s's Natural Cure activated!)", user.getNickname());
                    default:
                        return String.format("%s's %s heals its status!", user.getNickname(), ability.getName());
                }
            }

            if (item != null) {
                switch (status) {
                    case BURN:
                        return String.format("%s's %s healed its burn!", user.getNickname(), item.getName());
                    case FREEZE:
                        return String.format("%s's %s defrosted it!", user.getNickname(), item.getName());
                    case PARALYSIS:
                        return String.format("%s's %s cured its paralysis!", user.getNickname(), item.getName());
                    case POISON:
                    case BADLY_POISONED:
                        return String.format("%s's %s cured its poison!", user.getNickname(), item.getName());
                    case SLEEP:
                        return String.format("%s's %s woke it up!", user.getNickname(), item.getName());
                    default:
                        throw new IllegalArgumentException("Invalid status: " + status.getName());
                }
            } else {
                switch (status) {
                    case BURN:
                        return String.format("%s healed its burn!", user.getNickname());
                    case FREEZE:
                        return String.format("%s thawed out!", user.getNickname());
                    case PARALYSIS:
                        return String.format("%s was cured of paralysis.", user.getNickname());
                    case POISON:
                    case BADLY_POISONED:
                        return String.format("%s was cured of its poisoning.", user.getNickname());
                    case SLEEP:
                        return String.format("%s woke up!", user.getNickname());
                    default:
                        throw new IllegalArgumentException("Invalid status: " + status.getName());
                }
            }
        }

        @Nullable
        @Override
        public String getMessage(Pokemon user, VolatileStatus status) {
            return String.format("%s's status cleared!", user.getNickname());
        }
    },

    CURE_TEAM("Cure Team") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, @Nullable Item item, Status status, @Nullable Move move, @Nullable Ability ability) {
            if (move == null) {
                throw new IllegalArgumentException("Messages.CURE_TEAM called with null move");
            }

            switch (move.getBaseMove()) {
                case AROMATHERAPY:
                    return "A soothing aroma wafted through the area!";
                case HEAL_BELL:
                    return "A bell chimed!";
                default:
                    return String.format("%s's team was cursed!", user.getNickname());
            }
        }
    },

    ITEM("Item") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, Move move, Ability ability, Item item) {
            if (ability != null) {
                switch (ability) {
                    case PICKUP:
                        return String.format("%s found one %s!", user.getNickname(), item.getName());
                    case FRISK:
                        if (user.getBattle().getGeneration() == Generation.VI) {
                            return String.format("%s frisked %s and found its %s!", user.getNickname(), target.getNickname(), item.getName());
                        }

                        return String.format("%s frisked its target and found one %s!", user.getNickname(), item.getName());
                    case MAGICIAN:
                    case PICKPOCKET:
                        return String.format("%s stole %s's !", user.getNickname(), item.getName());
                    case HARVEST:
                        return String.format("%s harvested one %s!", user.getNickname(), item.getName());
                    default:
                        return String.format("%s obtained one %s.", user.getNickname(), item.getName());
                }
            }

            if (move != null) {
                switch (move.getBaseMove()) {
                    case RECYCLE:
                        return String.format("%s found one %s!", user.getNickname(), item.getName());
                    case THIEF:
                    case COVET:
                        return String.format("%s stole %s's !", user.getNickname(), item.getName());
                    case BESTOW:
                        return String.format("%s received %s from %s!", target.getNickname(), item.getName(), user.getNickname());
                    default:
                        return String.format("%s obtained one %s.", user.getNickname(), item.getName());
                }
            }

            if (item == Item.AIR_BALLOON) {
                return String.format("%s floats in the air with its Air Balloon!", user.getNickname());
            }

            return String.format("%s has %s!", user.getNickname(), item.getName());
        }
    },

    END_ITEM("End Item") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, Move move, Ability ability, Item item) {
            if (move != null) {
                switch (move.getBaseMove()) {
                    case FLING:
                        return String.format("%s flung its %s!", user.getNickname(), item.getName());
                    case KNOCK_OFF:
                        return String.format("%s knocked off %s's %s!", user.getNickname(), target.getNickname(), item.getName());
                    case BUG_BITE:
                    case PLUCK:
                        return String.format("%s stole and ate its target's %s!", user.getNickname(), item.getName());
                    case INCINERATE:
                        return String.format("%s's %s was burned up!", target.getNickname(), item.getName());
                    default:
                        return String.format("%s lost its %s!", user.getNickname(), item.getName());
                }
            }

            switch (item) {
                case AIR_BALLOON:
                    return String.format("%s's Air Balloon popped!", user.getNickname());
                case FOCUS_SASH:
                    return String.format("%s hung on using its Focus Sash!", user.getNickname());
                case FOCUS_BAND:
                    return String.format("%s hung on using its Focsu Band!", user.getNickname());
                case POWER_HERB:
                    return String.format("%s became fully charged due to its Power Herb!", user.getNickname());
                case WHITE_HERB:
                    return String.format("%s returned its status to normal using its White Herb!", user.getNickname());
                case EJECT_BUTTON:
                    return String.format("%s is switched out with the Eject Button!", user.getNickname());
                case RED_CARD:
                    return String.format("%s held up its Red Card against %s!", user.getNickname(), target.getNickname());
                case FIRE_GEM:
                case WATER_GEM:
                case ELECTRIC_GEM:
                case GRASS_GEM:
                case ICE_GEM:
                case FIGHTING_GEM:
                case POISON_GEM:
                case GROUND_GEM:
                case FLYING_GEM:
                case PSYCHIC_GEM:
                case BUG_GEM:
                case ROCK_GEM:
                case GHOST_GEM:
                case DRAGON_GEM:
                case DARK_GEM:
                case STEEL_GEM:
                case NORMAL_GEM:
                case FAIRY_GEM:
                    return String.format("The %s strengthened %s's power!", user.getNickname(), item.getName());
                default:
                    return String.format("%s's %s activated!", user.getNickname(), item.getName());
            }
        }
    },

    ABILITY("Ability") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, Move move, Ability ability) {
            String userName = user.getNickname();
            String targetName = target.getNickname();
            String targetAbilityName = target.getAbility().getName();
            String abilityName = ability.getName();

            if (ability != null) {
                switch (move.getBaseMove()) {
                    case ROLE_PLAY:
                        return String.format("%s copied %s's %s Ability!", userName, targetName, targetAbilityName);
                }

                switch (user.getAbility()) {
                    case TRACE:
                        return String.format("%s traced %s's %s!", userName, targetName, abilityName);
                    case POWER_OF_ALCHEMY:
                    case RECEIVER:
                        return String.format("%s's %s was taken over!", targetName, abilityName);
                    case DESOLATE_LAND:
                        return "The extremely harsh sunlight was not lessened at all!";
                    case PRIMORDIAL_SEA:
                        return "There's no relief from this heavy rain!";
                    case DELTA_STREAM:
                        return "The mysterious strong winds blow on regardless!";
                    default:
                        return String.format("%s acquired %s!", userName, abilityName);
                }
            }

            switch (user.getAbility()) {
                case AIR_LOCK:
                case CLOUD_NINE:
                    return "The effects of the weather disappeared!";
                case ANTICIPATION:
                    return String.format("%s shuddered!", targetName);
                case AURA_BREAK:
                    return String.format("%s reversed all other Pokemon's auras!", userName);
                case COMATOSE:
                    return String.format("%s is drowsing!", userName);
                case DARK_AURA:
                    return String.format("%s is radiating a dark aura!", userName);
                case FAIRY_AURA:
                    return String.format("%s is radiating a fairy aura!", userName);
                case MOLD_BREAKER:
                    return String.format("%s breaks the mold!", userName);
                case PRESSURE:
                    return String.format("%s is exerting its pressure!", userName);
                case STURDY:
                    return String.format("%s endured the hit!", userName);
                case TERAVOLT:
                    return String.format("%s is radiating a bursting aura!", userName);
                case TURBOBLAZE:
                    return String.format("%s is radiating a blazing aura!", userName);
                case UNNERVE:
                    return String.format("%s's team is too nervous to eat Berries!", target.getTrainer().getName());
            }

            throw new IllegalArgumentException(
                    "No valid message for:\n" +
                    "User ability: " + user.getAbility() + "\n" +
                    "Target ability: " + target.getAbility() + "\n" +
                    "Ability: " + ability
            );
        }
    },

    END_ABILITY("End Ability") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, Move move, Ability ability, boolean supressed) {
            if (supressed) {
                return String.format("%s's Ability was supressed!", target.getNickname());
            } else {
                return String.format("%s's %s was removed!", target.getNickname(), ability.getName());
            }
        }
    },

    TRANSFORM("Transform") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format("%s transformed into %s!", user.getNickname(), target.getName());
        }
    },

    FORM_ECHANGE("Form Echange") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return null;
        }
    },

    MEGA("Mega") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return null;
        }
    },

    PRIMAL("Primal") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return null;
        }
    },

    TYPE_CHANGE("Type Change") {
        @Nullable
        @Override
        public String getMessage(VolatileStatus status, Pokemon user, Pokemon target, Ability ability, Item item, Type type, Move targetMove, Move move, boolean zMove, boolean failed) {
            String userName = user.getNickname();
            String targetName = target.getNickname();
            String typeName = type.getName();

            if (ability != null) {
                return String.format("%s transformed into the %s type!", userName, typeName);
            }

            switch (move.getBaseMove()) {
                case REFLECT_TYPE:
                    return String.format("%s's type became the same as %s's type!", userName, targetName);
                case BURN_UP:
                    return String.format("%s burned itself out!", userName);
            }

            throw new IllegalArgumentException("Invalid type change for move " + move.getBaseMove().getName());
        }
    },

    TYPE_ADD("Type Add") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Type type) {
            return String.format("%s type was added to %s!", type.getName(), user.getNickname());
        }
    },

    ALREADY_CONFUSED("Already Confused") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format("%s is already confused!", target.getNickname());
        }
    },

    VOLATILE_START("Volatile Start") {
        @Nullable
        @Override
        public String getMessage(VolatileStatus status, Pokemon user, Pokemon target, Ability ability, Item item, Type type, Move targetMove, Move move, boolean zMove, boolean failed) {
            String userName = user.getNickname();
            String targetName = target.getNickname();

            if (status != null) {
                switch (status.getBaseVolatileStatus()) {
                    case STOCKPILE_1:
                        return String.format("%s stockpiled 1!", userName);
                    case STOCKPILE_2:
                        return String.format("%s stockpiled 2!", userName);
                    case STOCKPILE_3:
                        return String.format("%s stockpiled 3!", userName);
                    case PERISH_0:
                        return String.format("%s's perish count fell to 0.", userName);
                    case PERISH_1:
                        return String.format("%s's perish count fell to 1.", userName);
                    case PERISH_2:
                        return String.format("%s's perish count fell to 2.", userName);
                    case PERISH_3:
                        return String.format("%s's perish count fell to 3.", userName);
                    case INFATUATION:
                        if (item != null) {
                            return String.format("%s fell in love from the %s!", targetName, item.getName());
                        } else {
                            return String.format("%s fell in love!", targetName);
                        }
                    case SUBSTITUTE:
                        if (failed) {
                            return String.format("%s already has a substitute!", userName);
                        } else {
                            return String.format("%s put in a substitute!", userName);
                        }
                    case UPROAR:
                        return String.format("%s is making an uproar!", userName);
                }
            }

            switch (move.getBaseMove()) {
                case POWER_TRICK:
                    return String.format("%s switched its Attack and Defense", userName);
                case FORESIGHT:
                case MIRACLE_EYE:
                    return String.format("%s was identified!", targetName);
                case TELEKINESIS:
                    return String.format("%s was hurled into the air!", targetName);
                case OUTRAGE:
                case PETAL_DANCE:
                case THRASH:
                    return String.format("%s became confused due to fatigue!", targetName);
                case CONFUSION:
                    return String.format("%s became confused!", targetName);
                case LEECH_SEED:
                    return String.format("%s was seeded!", targetName);
                case HEAL_BLOCK:
                    return String.format("%s was prevented from healing!", targetName);
                case MUD_SPORT:
                    return "Electricity's power was weakened!";
                case WATER_SPORT:
                    return "Fire's power was weakened!";
                case YAWN:
                    return String.format("%s grew drowsy!", targetName);
                case TAUNT:
                    return String.format("%s fell for the taunt!", targetName);
                case IMPRISON:
                    return String.format("%s sealed any moves its target shares with it!", userName);
                case DISABLE:
                    return String.format("%s's %s was disabled!", targetName, targetMove.getBaseMove().getName());
                case EMBARGO:
                    return String.format("%s can't use items anymore!", targetName);
                case TORMENT:
                    return String.format("%s was subjected to torment", targetName);
                case INGRAIN:
                    return String.format("%s planted its roots!", userName);
                case AQUA_RING:
                    return String.format("%s surrounded itself with a veil of water!", userName);
                case ENCORE:
                    return String.format("%s received an encore!", targetName);
                case BIDE:
                    return String.format("%s is storing energy!", userName);
                case AUTOTOMIZE:
                    return String.format("%s became nimble!", userName);
                case FOCUS_ENERGY:
                    if (item != null) {
                        return String.format("%s used the %s to get pumped!", userName, item.getName());
                    } else if (zMove) {
                        return String.format("%s boosted its critical-hit ratio using its Z-Power!", userName);
                    } else {
                        return String.format("%s is getting pumped!", userName);
                    }
                case CURSE:
                    return String.format("%s cut its own HP and put a curse on %s!", userName, targetName);
                case NIGHTMARE:
                    return String.format("%s began having a nightmare!", targetName);
                case MAGNET_RISE:
                    return String.format("%s levitated with electromagnetism!", userName);
                case SMACK_DOWN:
                    return String.format("%s fell straight down!", targetName);
                case UPROAR:
                    return String.format("%s caused an uproar!", userName);
                case DOOM_DESIRE:
                    return String.format("%s chose Doom Desire as its destiny!", userName);
                case FUTURE_SIGHT:
                    return String.format("%s foresaw an attack!", userName);
                case MIMIC:
                    return String.format("%s learned %s!", userName, targetMove.getBaseMove().getName());
                case LASER_FOCUS:
                    return String.format("%s concentrated intensely", userName);
                case FOLLOW_ME:
                case RAGE_POWDER:
                    return String.format("%s became the center of attention!", userName);
                case POWDER:
                    return String.format("%s is covered in powder!", userName);
                case LIGHT_SCREEN:
                    if (user.getBattle().getGeneration() == Generation.I) {
                        return String.format("%s's protected against special attacks!", userName);
                    }
                case REFLECT:
                    if (user.getBattle().getGeneration() == Generation.I) {
                        return String.format("%s's gained armor!", userName);
                    }
            }

            switch (ability) {
                case FLASH_FIRE:
                    return String.format("The power of %s's Fire-type moves rose!", userName);
                case SLOW_START:
                    return String.format("%s can't get it going!", userName);
            }

            return String.format("%s's %s started!", userName, move.getBaseMove().getName());
        }
    },

    SUBSTITUTE_DAMAGED("Subtitute Damaged") {
        @Nullable
        @Override
        public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
            return String.format("The substitute took damage for %s!", target.getNickname());
        }
    };

    private final String NAME;
    protected final String MESSAGE;

    Messages(@Nonnull String name, @Nullable String message) {
        this.NAME = name;
        this.MESSAGE = message;
    }

    Messages(@Nonnull String name) {
        this(name, null);
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nullable
    public String getRawMessage() {
        return this.MESSAGE;
    }

    @Nullable
    public String getMessage(Pokemon user, Pokemon target, Battle battle, Trainer trainer, Move move, Item item, Ability ability, PermanentStat[] stat, Status status, VolatileStatus volatileStatus, boolean self) {
        return String.format(this.MESSAGE, user.getNickname());
    }

    @Nullable
    public String getMessage(Integer hitCount) {
        return null;
    }

    @Nullable
    public String getMessage(Pokemon user, Pokemon target, @Nullable Item item, Status status, @Nullable Move move, @Nullable Ability ability) {
        return null;
    }

    @Nullable
    public String getMessage(Pokemon user, VolatileStatus status) {
        return null;
    }

    @Nullable
    public String getMessage(Pokemon user, Pokemon target, Move move, Ability ability, Item item) {
        return null;
    }

    @Nullable
    public String getMessage(Pokemon user, Pokemon target, Move move, Ability ability) {
        return null;
    }

    @Nullable
    public String getMessage(Pokemon user, Pokemon target, Move move, Ability ability, boolean supressed) {
        return null;
    }

    @Nullable
    public String getMessage(VolatileStatus status, Pokemon user, Pokemon target, Ability ability, Item item, Type type, @Nullable Move targetMove, Move move, boolean zMove, boolean failed) {
        return null;
    }

    @Nullable
    public String getMessage(Pokemon user, Type type) {
        return null;
    }

}
