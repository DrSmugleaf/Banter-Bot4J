package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.pokemon.events.EventDispatcher;
import com.github.drsmugbrain.pokemon.events.PokemonDodgeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 17/06/2017.
 */
public enum BaseMove implements IBattle {

    SWITCH("Switch"),
    _10000000_VOLT_THUNDERBOLT("10,000,000 Volt Thunderbolt"),
    ABSORB("Absorb") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage = super.use(user, target, battle, action);
            user.heal(damage / 2);
            return damage;
        }
    },
    ACCELEROCK("Accelerock"),
    ACID("Acid") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage = super.use(user, target, battle, action);
            if (Math.random() < this.EFFECT_RATE) {
                target.lowerStatStage(Stat.SPECIAL_DEFENSE, 1);
            }
            return damage;
        }
    },
    ACID_ARMOR("Acid Armor") {
        @Override
        public int useAsZMove(@Nonnull Pokemon user, @Nullable Pokemon target, Battle battle, Action action) {
            user.resetLoweredStatStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    ACID_DOWNPOUR("Acid Downpour"),
    ACID_SPRAY("Acid Spray"),
    ACROBATICS("Acrobatics") { // Flying Gem is consumed before the power calculation is made
        @Override
        public int getPower(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
            if (target.getItem() == null) {
                return this.POWER * 2;
            } else {
                return this.POWER;
            }
        }
    },
    ACUPRESSURE("Acupressure") {
        @Override
        protected int useAsZMove(@Nonnull Pokemon user, @Nullable Pokemon target, Battle battle, Action action) {
            user.raiseCriticalHitStage(1);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    AERIAL_ACE("Aerial Ace"), // Always hit except semi invulnerable targets
    AEROBLAST("Aeroblast"),
    AFTER_YOU("After You") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            List<Action> turnOrder = battle.getTurnOrder();
            Action userAction = battle.getAction(user);
            Action targetAction = battle.getAction(target);
            int userActionIndex = turnOrder.indexOf(userAction);
            int targetActionIndex = turnOrder.indexOf(targetAction);

            if (targetActionIndex > userActionIndex) {
                battle.getTurnOrder().add(userActionIndex + 1, battle.getTurnOrder().remove(targetActionIndex));
                return super.use(user, target, battle, action);
            } else {
                return miss(target);
            }
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, Battle battle, Action action) {
            user.raiseStatStage(Stat.SPEED, 1);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    AGILITY("Agility") {
        @Override
        public int useAsZMove(@Nonnull Pokemon user, @Nullable Pokemon target, @Nullable Battle battle, Action action) {
            user.resetLoweredStatStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    AIR_CUTTER("Air Cutter"),
    AIR_SLASH("Air Slash"),
    ALL_OUT_PUMMELING("All-Out Pummeling"),
    ALLY_SWITCH("Ally Switch") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.getTrainer().swapActivePokemonPlaces(user, target);
            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.raiseStatStage(Stat.SPEED, 2);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    AMNESIA("Amnesia") {
        @Override
        public int useAsZMove(@Nonnull Pokemon user, @Nullable Pokemon target, @Nullable Battle battle, Action action) {
            user.resetLoweredStatStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    ANCHOR_SHOT("Anchor Shot"),
    ANCIENT_POWER("Ancient Power"),
    AQUA_JET("Aqua Jet"),
    AQUA_RING("Aqua Ring") {
        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.raiseStatStage(Stat.DEFENSE, 1);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    AQUA_TAIL("Aqua Tail"),
    ARM_THRUST("Arm Thrust") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Integer repeats;
            double chance = Math.random();
            if (chance < 0.375) {
                repeats = 1;
            } else if (chance < 0.75) {
                repeats = 2;
            } else if (chance < 0.875) {
                repeats = 3;
            } else {
                repeats = 4;
            }

            return super.use(user, target, battle, action, repeats);
        }
    },
    AROMATHERAPY("Aromatherapy") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            for (Pokemon pokemon : user.getTrainer().getPokemons()) {
                pokemon.resetStatus();
            }

            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.heal(100.0);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    AROMATIC_MIST("Aromatic Mist") {
        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.raiseStatStage(Stat.SPECIAL_DEFENSE, 2);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    ASSIST("Assist") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            List<Move> teamMoves = new ArrayList<>();
            for (Pokemon pokemon : user.getTrainer().getPokemons()) {
                teamMoves.addAll(pokemon.getMoves());
            }
            teamMoves.removeIf((teamMove) -> {
                BaseMove baseMove = teamMove.getBaseMove();
                switch (baseMove) {
                    case ASSIST:
                    case BELCH:
                    case BOUNCE:
                    case CHATTER:
                    case CIRCLE_THROW:
                    case COPYCAT:
                    case COUNTER:
                    case COVET:
                    case DESTINY_BOND:
                    case DETECT:
                    case DIG:
                    case DIVE:
                    case DRAGON_TAIL:
                    case ENDURE:
                    case FEINT:
                    case FLY:
                    case FOCUS_PUNCH:
                    case FOLLOW_ME:
                    case HELPING_HAND:
                    case KINGS_SHIELD:
                    case MAT_BLOCK:
                    case ME_FIRST:
                    case METRONOME:
                    case MIMIC:
                    case MIRROR_COAT:
                    case MIRROR_MOVE:
                    case NATURE_POWER:
                    case PHANTOM_FORCE:
                    case PROTECT:
                    case ROAR:
                    case SHADOW_FORCE:
                    case SKETCH:
                    case SKY_DROP:
                    case SLEEP_TALK:
                    case SNATCH:
                    case SPIKY_SHIELD:
                    case STRUGGLE:
                    case SWITCHEROO:
                    case THIEF:
                    case TRANSFORM:
                    case TRICK:
                    case WHIRLWIND:
                        return true;
                    default:
                        return false;
                }
            });

            if (teamMoves.isEmpty()) {
                return this.miss(target);
            }

            Move randomMove = teamMoves.get(new Random().nextInt(teamMoves.size()));
            return randomMove.getBaseMove().use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.removeItem();

            List<Move> teamMoves = new ArrayList<>();
            for (Pokemon pokemon : user.getTrainer().getPokemons()) {
                teamMoves.addAll(pokemon.getMoves());
            }
            teamMoves.removeIf((teamMove) -> {
                BaseMove baseMove = teamMove.getBaseMove();
                switch (baseMove) {
                    case ASSIST:
                    case BELCH:
                    case BOUNCE:
                    case CHATTER:
                    case CIRCLE_THROW:
                    case COPYCAT:
                    case COUNTER:
                    case COVET:
                    case DESTINY_BOND:
                    case DETECT:
                    case DIG:
                    case DIVE:
                    case DRAGON_TAIL:
                    case ENDURE:
                    case FEINT:
                    case FLY:
                    case FOCUS_PUNCH:
                    case FOLLOW_ME:
                    case HELPING_HAND:
                    case KINGS_SHIELD:
                    case MAT_BLOCK:
                    case ME_FIRST:
                    case METRONOME:
                    case MIMIC:
                    case MIRROR_COAT:
                    case MIRROR_MOVE:
                    case NATURE_POWER:
                    case PHANTOM_FORCE:
                    case PROTECT:
                    case ROAR:
                    case SHADOW_FORCE:
                    case SKETCH:
                    case SKY_DROP:
                    case SLEEP_TALK:
                    case SNATCH:
                    case SPIKY_SHIELD:
                    case STRUGGLE:
                    case SWITCHEROO:
                    case THIEF:
                    case TRANSFORM:
                    case TRICK:
                    case WHIRLWIND:
                        return true;
                    default:
                        return false;
                }
            });

            if (teamMoves.isEmpty()) {
                return this.miss(target);
            }

            Move randomMove = teamMoves.get(new Random().nextInt(teamMoves.size()));
            return randomMove.getBaseMove().useAsZMove(user, target, battle, action
            );
        }
    },
    ASSURANCE("Assurance") {
        @Override
        public int getPower(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
            if (target.isDamagedThisTurn()) {
                return super.getPower(user, target, battle, trainer) * 2;
            }

            return super.getPower(user, target, battle, trainer);
        }
    },
    ASTONISH("Astonish"),
    ATTACK_ORDER("Attack Order"),
    ATTRACT("Attract") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (Gender.isOppositeGender(user, target)) {
                BaseVolatileStatus.INFATUATION.apply(user, target, action);
                return super.use(user, target, battle, action);
            } else {
                return this.miss(target);
            }
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.resetLoweredStatStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    AURA_SPHERE("Aura Sphere"),
    AURORA_BEAM("Aurora Beam"),
    AURORA_VEIL("Aurora Veil") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (battle.getWeather() != Weather.HAIL) {
                return this.miss(target);
            }

            BaseVolatileStatus.AURORA_VEIL.apply(user, target, action);
            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.raiseStatStage(Stat.SPEED, 1);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    AUTOTOMIZE("Autotomize") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.setWeight(50);
            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.resetLoweredStatStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    AVALANCHE("Avalanche") { // If the target had already damaged the user in the same turn, Avalanche's power is doubled to 120.
        @Override
        public int getPower(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
            if (user.isDamagedThisTurnBy(target)) {
                return super.getPower(user, target, battle, trainer) * 2;
            }

            return super.getPower(user, target, battle, trainer);
        }
    },
    BABY_DOLL_EYES("Baby-Doll Eyes"),
    BANEFUL_BUNKER("Baneful Bunker"),
    BARRAGE("Barrage") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Integer repeats;
            double chance = Math.random();
            if (chance < 0.375) {
                repeats = 1;
            } else if (chance < 0.75) {
                repeats = 2;
            } else if (chance < 0.875) {
                repeats = 3;
            } else {
                repeats = 4;
            }

            return super.use(user, target, battle, action, repeats);
        }
    },
    BARRIER("Barrier") {
        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.resetLoweredStatStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    BATON_PASS("Baton Pass") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (user.getTrainer().getAliveInactivePokemons().isEmpty()) {
                return this.miss(target);
            }

            Collection<VolatileStatus> volatileStatuses = user.getVolatileStatuses().values();
            volatileStatuses.removeIf(status -> status.getBaseVolatileStatus() == BaseVolatileStatus.INFATUATION);

            target.addVolatileStatus(volatileStatuses);
            target.setStatStage(user.getStatStages());
            user.getTrainer().switchPokemon(user, target);
            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.resetLoweredStatStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    BEAK_BLAST("Beak Blast") {
        @Override
        public void onTurnStart(@Nonnull Battle battle, @Nonnull Pokemon user) {
            BaseVolatileStatus.BEAK_BLAST.apply(user, user, null);
        }

        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            BaseVolatileStatus.BEAK_BLAST.remove(user, target, action);
            return super.use(user, target, battle, action);
        }
    },
    BEAT_UP("Beat Up") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            List<Pokemon> validPokemons = user.getTrainer().getAlivePokemons();
            validPokemons.removeIf(pokemon -> pokemon.getStatus() != null);

            int totalDamage = 0;
            int damage;
            for (Pokemon pokemon : validPokemons) {
                double stabMultiplier = 1.0;
                if (pokemon.getTypes().contains(this.getType())) {
                    stabMultiplier = pokemon.getStabMultiplier(action);
                }
                damage = (int) (((pokemon.getStat(Stat.ATTACK) / 10) + 5) * stabMultiplier);
                totalDamage += damage;
                target.damage(damage);
            }

            return totalDamage;
        }
    },
    BELCH("Belch") {
        @Override
        public void onOwnItemUsed(@Nonnull Pokemon user, @Nonnull Item item) {
            if (item.getCategory() == ItemCategory.BERRY) {
                user.addTag(Tag.BERRY_USED);
            }
        }

        @Override
        public boolean canUseMove(@Nonnull Pokemon user) {
            return user.hasTag(Tag.BERRY_USED);
        }
    },
    BELLY_DRUM("Belly Drum") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.damage(50.0);
            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.heal(100.0);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    BESTOW("Bestow") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (target.hasItem() || user.getItem() == null) {
                return this.miss(target);
            }

            target.setItem(user.getItem());
            user.removeItem();
            return super.use(user, target, battle, action);
        }
    },
    BIDE("Bide") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            BaseVolatileStatus.BIDE.apply(user, target, action);
            return super.use(user, target, battle, action);
        }
    },
    BIND("Bind") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Action lastAction = user.getLastAction();

            if (!target.hasVolatileStatus(BaseVolatileStatus.BOUND)) {
                BaseVolatileStatus.BOUND.apply(user, target, action);
            } else if (lastAction.getBaseMove() == BaseMove.BIND &&
                       lastAction.getDefender() == target) {
                action.increasePP(1);
            }

            return super.use(user, target, battle, action);
        }

        @Override
        protected int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, Move move) {
            switch (attacker.getBattle().getGeneration()) {
                case I:
                    if (defender.isImmune(move)) {
                        return 0;
                    }

                    return super.getDamage(attacker, defender, move);
                case II:
                case III:
                case IV:
                case V:
                    return defender.getStat(Stat.HP) / 16;
                case VI:
                case VII:
                    return defender.getStat(Stat.HP) / 8;
                default:
                    throw new InvalidGenerationException(attacker.getBattle().getGeneration());
            }
        }

        @Override
        public boolean hits(Pokemon attacker, Pokemon defender, Battle battle, Move move) {
            switch (battle.getGeneration()) {
                case I:
                    if (this.getAccuracy() == 0) {
                        return true;
                    }

                    if (battle.getGeneration() == Generation.I) {
                        if (Math.random() < 0.004) {
                            return false;
                        }
                    }

                    double probability = this.getAccuracy() * (attacker.getAccuracy() / defender.getEvasion());
                    if (probability > 1 || probability < Math.random()) {
                        return true;
                    }

                    return false;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    return super.hits(attacker, defender, battle, move);
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }
        }
    },
    BITE("Bite"),
    BLACK_HOLE_ECLIPSE("Black Hole Eclipse"),
    BLAST_BURN("Blast Burn"),
    BLAZE_KICK("Blaze Kick"),
    BLIZZARD("Blizzard"),
    BLOCK("Block") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.raiseStatStage(Stat.DEFENSE, 1);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    BLOOM_DOOM("Bloom Doom"),
    BLUE_FLARE("Blue Flare"),
    BODY_SLAM("Body Slam"),
    BOLT_STRIKE("Bolt Strike"),
    BONE_CLUB("Bone Club"),
    BONE_RUSH("Bone Rush") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Integer repeats;
            double chance = Math.random();
            if (chance < 0.375) {
                repeats = 1;
            } else if (chance < 0.75) {
                repeats = 2;
            } else if (chance < 0.875) {
                repeats = 3;
            } else {
                repeats = 4;
            }

            return super.use(user, target, battle, action, repeats);
        }
    },
    BONEMERANG("Bonemerang") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            return super.use(user, target, battle, action, 2);
        }
    },
    BOOMBURST("Boomburst"),
    BOUNCE("Bounce") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (!user.hasVolatileStatus(BaseVolatileStatus.BOUNCE)) {
                VolatileStatus status = new VolatileStatus(BaseVolatileStatus.BOUNCE, action, 2);
                user.addVolatileStatus(status);
                return 0;
            }

            return super.use(user, target, battle, action);
        }
    },
    BRAVE_BIRD("Brave Bird") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage = super.use(user, target, battle, action);
            if (damage > 0) {
                user.damage(damage / 3);
            }
            return damage;
        }
    },
    BREAKNECK_BLITZ("Breakneck Blitz"),
    BRICK_BREAK("Brick Break") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            switch (battle.getGeneration()) {
                case I:
                case II:
                    throw new InvalidGenerationException(battle.getGeneration());
                case III:
                    if (target.getTrainer() == user.getTrainer()) {
                        break;
                    }
                case IV:
                    for (Pokemon pokemon : target.getTrainer().getActivePokemons()) {
                        pokemon.removeVolatileStatus(BaseVolatileStatus.LIGHT_SCREEN, BaseVolatileStatus.REFLECT);
                    }
                    break;
                case V:
                case VI:
                case VII:
                    if (target.isImmune(action)) {
                        break;
                    }

                    for (Pokemon pokemon : target.getTrainer().getActivePokemons()) {
                        pokemon.removeVolatileStatus(BaseVolatileStatus.LIGHT_SCREEN, BaseVolatileStatus.REFLECT, BaseVolatileStatus.AURORA_VEIL);
                    }
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            return super.use(user, target, battle, action);
        }
    },
    BRINE("Brine") {
        @Override
        public int getPower(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
            int defenderCurrentHP = target.getCurrentStat(Stat.HP);
            int defenderMaxHP = target.getStat(Stat.HP);
            if (defenderCurrentHP <= defenderMaxHP / 2) {
                return this.POWER * 2;
            } else {
                return this.POWER;
            }
        }
    },
    BRUTAL_SWING("Brutal Swing"),
    BUBBLE("Bubble"),
    BUBBLE_BEAM("Bubble Beam"),
    BUG_BITE("Bug Bite") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Item berry = target.getItem();
            target.removeItem();
            berry.use(user, battle);
            return super.use(user, target, battle, action);
        }
    },
    BUG_BUZZ("Bug Buzz"),
    BULK_UP("Bulk Up"),
    BULLDOZE("Bulldoze"),
    BULLET_PUNCH("Bullet Punch"),
    BULLET_SEED("Bullet Seed") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Integer repeats;
            double chance = Math.random();
            if (chance < 0.375) {
                repeats = 1;
            } else if (chance < 0.75) {
                repeats = 2;
            } else if (chance < 0.875) {
                repeats = 3;
            } else {
                repeats = 4;
            }

            return super.use(user, target, battle, action, repeats);
        }
    },
    BURN_UP("Burn Up") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            switch (battle.getGeneration()) {
                case I:
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    if (user.getStatus() == Status.FREEZE) {
                        Status.FREEZE.remove(user);
                    }
                    break;
            }

            List<Type> types = user.getTypes();
            if (types.contains(Type.FIRE)) {
                types.remove(Type.FIRE);
                if (types.size() == 0) {
                    types.add(Type.TYPELESS);
                }

                user.setTypes(types);
            }

            return super.use(user, target, battle, action);
        }

        @Override
        public boolean hits(Pokemon attacker, Pokemon defender, Battle battle, Move move) {
            if (!attacker.isType(Type.FIRE)) {
                return false;
            }

            return super.hits(attacker, defender, battle, move);
        }
    },
    CALM_MIND("Calm Mind"),
    CAMOUFLAGE("Camouflage") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.setTypes(Type.NORMAL);
            return super.use(user, target, battle, action);
        }
    },
    CAPTIVATE("Captivate") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (!Gender.isInfatuatable(user, target)) {
                return miss(target);
            }

            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.raiseStatStage(Stat.SPECIAL_DEFENSE, 2);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    CATASTROPIKA("Catastropika"),
    CELEBRATE("Celebrate") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.raiseStatStage(Stat.ATTACK, 1);
            user.raiseStatStage(Stat.DEFENSE, 1);
            user.raiseStatStage(Stat.SPECIAL_ATTACK, 1);
            user.raiseStatStage(Stat.SPECIAL_DEFENSE, 1);
            user.raiseStatStage(Stat.SPEED, 1);

            return super.useAsZMove(user, target, battle, action);
        }
    },
    CHARGE("Charge") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            BaseVolatileStatus.CHARGE.apply(user, target, action);

            switch (battle.getGeneration()) {
                case I:
                case II:
                case III:
                    break;
                case IV:
                case V:
                case VI:
                case VII:
                    user.raiseStatStage(Stat.SPECIAL_DEFENSE, 1);
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.raiseStatStage(Stat.SPECIAL_DEFENSE, 1);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    CHARGE_BEAM("Charge Beam"),
    CHARM("Charm"),
    CHATTER("Chatter"),
    CHIP_AWAY("Chip Away") {
        @Override
        protected int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, Move move) {
            if (this.getCategory() == Category.OTHER) {
                return 0;
            }

            int attackStat;
            int defenseStat;
            int level = attacker.getLevel();
            int attackPower = this.POWER;
            double stabMultiplier = attacker.getStabMultiplier(move);
            double effectiveness = Type.getDamageMultiplier(defender.getTypes(), this.getType());
            double randomNumber = ThreadLocalRandom.current().nextDouble(0.85, 1.0);

            if (this.getCategory() == Category.PHYSICAL) {
                attackStat = attacker.getStat(Stat.ATTACK);
                defenseStat = defender.getStatWithoutStages(Stat.DEFENSE);
            } else if (this.getCategory() == Category.SPECIAL) {
                attackStat = attacker.getStat(Stat.SPECIAL_ATTACK);
                defenseStat = defender.getStatWithoutStages(Stat.SPECIAL_DEFENSE);
            } else {
                throw new InvalidCategoryException(this.getCategory());
            }

            return (int) (((((2 * level / 5 + 2) * attackStat * attackPower / defenseStat) / 50) + 2) * stabMultiplier * effectiveness * randomNumber);
        }

        @Override
        public boolean hits(Pokemon attacker, Pokemon defender, Battle battle, Move move) {
            if (this.getAccuracy() == 0) {
                return true;
            }

            Generation generation = attacker.getBattle().getGeneration();
            if (generation == Generation.I) {
                if (Math.random() < 0.004) {
                    return false;
                }
            }

            double probability = this.getAccuracy() * attacker.getAccuracy();
            if (probability > 1 || probability < Math.random()) {
                return true;
            }

            return false;
        }
    },
    CIRCLE_THROW("Circle Throw") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage = super.use(user, target, battle, action);

            if (user.isFainted()) {
                return damage;
            }

            Pokemon nextpokemon = user.getTrainer().getNextAliveUnactivePokemon();
            if (nextpokemon != null) {
                user.getTrainer().switchPokemon(nextpokemon, target);
            }

            return damage;
        }
    },
    CLAMP("Clamp") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Action lastAction = user.getLastAction();

            if (!target.hasVolatileStatus(BaseVolatileStatus.BOUND)) {
                BaseVolatileStatus.BOUND.apply(user, target, action);
            } else if (lastAction.getBaseMove() == BaseMove.CLAMP &&
                       lastAction.getDefender() == target) {
                action.increasePP(1);
            }

            return super.use(user, target, battle, action);
        }

        @Override
        protected int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, Move move) {
            switch (attacker.getBattle().getGeneration()) {
                case I:
                    if (defender.isImmune(move)) {
                        return 0;
                    }

                    return super.getDamage(attacker, defender, move);
                case II:
                case III:
                case IV:
                case V:
                    return defender.getStat(Stat.HP) / 16;
                case VI:
                case VII:
                    return defender.getStat(Stat.HP) / 8;
                default:
                    throw new InvalidGenerationException(attacker.getBattle().getGeneration());
            }
        }

        @Override
        public boolean hits(Pokemon attacker, Pokemon defender, Battle battle, Move move) {
            switch (battle.getGeneration()) {
                case I:
                    if (this.getAccuracy() == 0) {
                        return true;
                    }

                    if (battle.getGeneration() == Generation.I) {
                        if (Math.random() < 0.004) {
                            return false;
                        }
                    }

                    double probability = this.getAccuracy() * (attacker.getAccuracy() / defender.getEvasion());
                    if (probability > 1 || probability < Math.random()) {
                        return true;
                    }

                    return false;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    return super.hits(attacker, defender, battle, move);
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }
        }
    },
    CLANGING_SCALES("Clanging Scales"),
    CLEAR_SMOG("Clear Smog") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            target.resetStatStages();
            return super.use(user, target, battle, action);
        }
    },
    CLOSE_COMBAT("Close Combat"),
    COIL("Coil") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.resetLoweredStatStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    COMET_PUNCH("Comet Punch") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Integer repeats;
            double chance = Math.random();

            switch (battle.getGeneration()) {
                case I:
                case II:
                case III:
                case IV:
                    if (chance < 0.375) {
                        repeats = 1;
                    } else if (chance < 0.75) {
                        repeats = 2;
                    } else if (chance < 0.875) {
                        repeats = 3;
                    } else {
                        repeats = 4;
                    }
                    break;
                case V:
                case VI:
                case VII:
                    if (chance < 0.33) {
                        repeats = 1;
                    } else if (chance < 0.66) {
                        repeats = 2;
                    } else if (chance < 0.833) {
                        repeats = 3;
                    } else {
                        repeats = 4;
                    }
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            return super.use(user, target, battle, action, repeats);
        }

        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action, @Nonnull Integer repeats) {
            switch (battle.getGeneration()) {
                case I:
                case II:
                    if (!target.hasVolatileStatus(BaseVolatileStatus.SUBSTITUTE)) {
                        return super.use(user, target, battle, action, repeats);
                    }

                    Integer firstHitDamage = null;
                    int damage = 0;
                    for (int i = 0; i < repeats; i++) {
                        damage += this.use(user, target, battle, action);
                        if (firstHitDamage == null) {
                            firstHitDamage = damage;
                        } else {
                            this.use(target, firstHitDamage);
                        }

                        if (!target.hasVolatileStatus(BaseVolatileStatus.SUBSTITUTE)) {
                            break;
                        }
                    }

                    return damage;
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    return super.use(user, target, battle, action, repeats);
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }
        }
    },
    CONFIDE("Confide") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.raiseStatStage(Stat.SPECIAL_DEFENSE, 1);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    CONFUSE_RAY("Confuse Ray") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.raiseStatStage(Stat.SPECIAL_ATTACK, 1);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    CONFUSION("Confusion"),
    CONSTRICT("Constrict"),
    CONTINENTAL_CRUSH("Continental Crush"),
    CONVERSION("Conversion") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            switch (battle.getGeneration()) {
                case I:
                    user.setTypes(target.getTypes());
                    break;
                case II:
                case III:
                case IV: {
                    List<Type> moveTypes = user.getMoves().stream().map(Move::getType).collect(Collectors.toList());
                    moveTypes.remove(Type.CURSE);

                    int randomIndex = ThreadLocalRandom.current().nextInt(moveTypes.size());
                    Type randomType = moveTypes.get(randomIndex);

                    user.setTypes(randomType);
                    break;
                }
                case V: {
                    List<Type> moveTypes = user.getMoves().stream().map(Move::getType).collect(Collectors.toList());

                    int randomIndex = ThreadLocalRandom.current().nextInt(moveTypes.size());
                    Type randomType = moveTypes.get(randomIndex);

                    user.setTypes(randomType);
                    break;
                }
                case VI:
                case VII:
                    Type firstMoveType = user.getMoves().get(0).getType();
                    user.setTypes(firstMoveType);
                    break;
            }

            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.raiseStatStage(Stat.ATTACK, 1);
            user.raiseStatStage(Stat.DEFENSE, 1);
            user.raiseStatStage(Stat.SPECIAL_ATTACK, 1);
            user.raiseStatStage(Stat.SPECIAL_DEFENSE, 1);
            user.raiseStatStage(Stat.SPEED, 1);

            return super.useAsZMove(user, target, battle, action);
        }
    },
    CONVERSION_2("Conversion 2") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Move lastMove = null;
            List<Type> typesResisted = new ArrayList<>();
            Type moveType;

            switch (battle.getGeneration()) {
                case I:
                case II:
                case III:
                case IV:
                    List<Action> movesHitBy = user.getHitBy();

                    for (int i = movesHitBy.size() - 1; i >= 0; i--) {
                        Move currentMove = movesHitBy.get(i);

                        if (currentMove.getCategory() != Category.OTHER) {
                            lastMove = currentMove;
                            break;
                        }
                    }

                    if (lastMove == null) {
                        return this.miss(target);
                    }

                    moveType = lastMove.getType();
                    typesResisted.addAll(moveType.getResistances());
                    typesResisted.addAll(moveType.getImmunities());
                    break;
                case V:
                case VI:
                case VII:
                    lastMove = target.getLastAction();

                    if (lastMove == null) {
                        return this.miss(target);
                    }

                    moveType = lastMove.getType();
                    typesResisted.addAll(moveType.getResistances());
                    typesResisted.addAll(moveType.getImmunities());

                    typesResisted.removeAll(user.getTypes());

                    if (typesResisted.isEmpty()) {
                        return this.miss(target);
                    }
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            int randomIndex = ThreadLocalRandom.current().nextInt(typesResisted.size());
            Type randomType = typesResisted.get(randomIndex);

            user.setTypes(randomType);

            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.heal(100.0);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    COPYCAT("Copycat") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Move copiedMove = null;

            Action lastAction = battle.getLastAction();
            if (lastAction != null) {
                Move lastMove = lastAction.getMove();

                switch (lastMove.getBaseMove()) {
                    case ASSIST:
                    case CHATTER:
                    case COPYCAT:
                    case COUNTER:
                    case COVET:
                    case DESTINY_BOND:
                    case DETECT:
                    case ENDURE:
                    case FEINT:
                    case FOCUS_PUNCH:
                    case FOLLOW_ME:
                    case HELPING_HAND:
                    case ME_FIRST:
                    case METRONOME:
                    case MIMIC:
                    case MIRROR_COAT:
                    case MIRROR_MOVE:
                    case PROTECT:
                    case SKETCH:
                    case SLEEP_TALK:
                    case SNATCH:
                    case STRUGGLE:
                    case SWITCHEROO:
                    case THIEF:
                    case TRICK:
                        copiedMove = lastMove;
                        break;
                    default:
                        copiedMove = null;
                }
            }

            if (copiedMove == null) {
                return miss(target);
            }

            return copiedMove.tryUse(user, target);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.raiseStatStage(BattleStat.ACCURACY, 1);

            Move copiedMove = null;

            Action lastAction = battle.getLastAction();
            if (lastAction != null) {
                Move lastMove = lastAction.getMove();

                switch (lastMove.getBaseMove()) {
                    case ASSIST:
                    case CHATTER:
                    case COPYCAT:
                    case COUNTER:
                    case COVET:
                    case DESTINY_BOND:
                    case DETECT:
                    case ENDURE:
                    case FEINT:
                    case FOCUS_PUNCH:
                    case FOLLOW_ME:
                    case HELPING_HAND:
                    case ME_FIRST:
                    case METRONOME:
                    case MIMIC:
                    case MIRROR_COAT:
                    case MIRROR_MOVE:
                    case PROTECT:
                    case SKETCH:
                    case SLEEP_TALK:
                    case SNATCH:
                    case STRUGGLE:
                    case SWITCHEROO:
                    case THIEF:
                    case TRICK:
                        copiedMove = lastMove;
                        break;
                    default:
                        copiedMove = null;
                }
            }

            if (copiedMove == null) {
                return super.miss(user);
            }

            user.removeItem();
            if (copiedMove.getCategory() != Category.OTHER) {
                return copiedMove.useAsZMove(user, target);
            } else {
                return copiedMove.tryUse(user, target);
            }
        }
    },
    CORE_ENFORCER("Core Enforcer") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (target.movedThisTurn()) {
                switch (target.getAbility()) {
                    case MULTITYPE:
                    case STANCE_CHANGE:
                    case SCHOOLING:
                    case COMATOSE:
                    case SHIELDS_DOWN:
                    case DISGUISE:
                    case RKS_SYSTEM:
                    case BATTLE_BOND:
                    case POWER_CONSTRUCT:
                        break;
                    default:
                        target.setAbilitySuppressed(true);
                }
            }

            return super.use(user, target, battle, action);
        }
    },
    CORKSCREW_CRASH("Corkscrew Crash"),
    COSMIC_POWER("Cosmic Power") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.raiseStatStage(Stat.SPECIAL_DEFENSE, 1);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    COTTON_GUARD("Cotton Guard") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.resetLoweredStatStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    COTTON_SPORE("Cotton Spore") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.resetLoweredStatStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    COUNTER("Counter") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Action lastAction = user.getLastHitBy();
            if (lastAction == null) {
                return this.miss(user);
            }

            switch (battle.getGeneration()) {
                case I:
                    if (lastAction.getDamage() > 0
                        && (lastAction.getType() == Type.NORMAL || lastAction.getType() == Type.FIGHTING)
                        && lastAction.getBaseMove() != COUNTER) {
                        int damage;
                        if (lastAction.getDefenderVolatileStatuses().contains(BaseVolatileStatus.SUBSTITUTE)) {
                            damage = lastAction.getBaseMove().getDamage(lastAction.getAttacker(), lastAction.getDefender(), lastAction.getMove());
                        } else {
                            damage = lastAction.getDamage() * 2;
                        }
                        target.damage(damage);
                        return damage;
                    }
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                    if (lastAction.getDefenderVolatileStatuses().contains(BaseVolatileStatus.SUBSTITUTE)) {
                        return this.miss(user);
                    }

                    if (lastAction.getCategory() == Category.PHYSICAL) {
                        int damage = lastAction.getDamage() * 2;
                        target.damage(damage);
                        return damage;
                    }
                    break;
                case VII:
                    if (lastAction.getDefenderVolatileStatuses().contains(BaseVolatileStatus.SUBSTITUTE)) {
                        return this.miss(user);
                    }

                    if (lastAction.getCategory() == Category.PHYSICAL) {
                        int damage = lastAction.getDamage() * 2;
                        if (damage == 0) {
                            damage = 1;
                        }
                        target.damage(damage);
                        return damage;
                    }
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            return this.miss(user);
        }

        @Override
        public boolean hits(Pokemon attacker, Pokemon defender, Battle battle, Move move) {
            Generation generation = attacker.getBattle().getGeneration();

            if (generation != Generation.I) {
                return super.hits(attacker, defender, battle, move);
            }

            if (this.getAccuracy() == 0) {
                return true;
            }

            if (Math.random() < 0.004) {
                return false;
            }

            double probability = this.getAccuracy() * (attacker.getAccuracy() / defender.getEvasion());
            if (probability > 1 || probability < Math.random()) {
                return true;
            }

            return false;
        }
    },
    COVET("Covet") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage = super.use(user, target, battle, action);

            Item targetItem = target.getItem();
            ItemCategory targetItemCategory = targetItem.getCategory();
            if (!user.hasItem() && target.hasItem() && !target.hasVolatileStatus(BaseVolatileStatus.SUBSTITUTE)) {
                switch (battle.getGeneration()) {
                    case I:
                    case II:
                    case III:
                        target.stealItem(user);
                        break;
                    case IV:
                        if (target.getAbility() == Ability.MULTITYPE || targetItem == Item.GRISEOUS_ORB) {
                            break;
                        }

                        target.stealItem(user);
                        break;
                    case V: {
                        if (user.isFainted()) {
                            break;
                        }

                        Pokemons userPokemon = user.getBasePokemon();
                        Pokemons targetPokemon = target.getBasePokemon();
                        if (targetItem == Item.GRISEOUS_ORB) {
                            if (userPokemon == Pokemons.GIRATINA || targetPokemon == Pokemons.GIRATINA) {
                                break;
                            }
                        }

                        if (targetItemCategory == ItemCategory.ARCEUS_PLATE) {
                            if (Pokemons.isArceus(user) || Pokemons.isArceus(target)) {
                                break;
                            }
                        }

                        if (targetItemCategory == ItemCategory.GENESECT_DRIVE) {
                            if (user.getBasePokemon() == Pokemons.GENESECT || target.getBasePokemon() == Pokemons.GENESECT) {
                                break;
                            }
                        }

                        target.stealItem(user);
                        break;
                    }
                    case VI:
                    case VII: {
                        if (user.isFainted()) {
                            break;
                        }

                        Pokemons userPokemon = user.getBasePokemon();
                        Pokemons targetPokemon = target.getBasePokemon();
                        if (targetItem == Item.GRISEOUS_ORB) {
                            if (userPokemon == Pokemons.GIRATINA || targetPokemon == Pokemons.GIRATINA) {
                                break;
                            }
                        }

                        if (targetItemCategory == ItemCategory.ARCEUS_PLATE) {
                            if (Pokemons.isArceus(user) || Pokemons.isArceus(target)) {
                                break;
                            }
                        }

                        if (targetItemCategory == ItemCategory.GENESECT_DRIVE) {
                            if (user.getBasePokemon() == Pokemons.GENESECT || target.getBasePokemon() == Pokemons.GENESECT) {
                                break;
                            }
                        }

                        if (targetItemCategory == ItemCategory.MEGA_STONE || targetItemCategory == ItemCategory.PRIMAL_ORB) {
                            break;
                        }

                        if (targetItemCategory == ItemCategory.Z_CRYSTAL) {
                            break;
                        }

                        if (targetItemCategory == ItemCategory.SILVALLY_MEMORY) {
                            if (Pokemons.isSilvally(user) || Pokemons.isSilvally(target)) {
                                break;
                            }
                        }

                        target.stealItem(user);
                        break;
                    }
                    default:
                        throw new InvalidGenerationException(battle.getGeneration());
                }
            }

            return damage;
        }
    },
    CRABHAMMER("Crabhammer"),
    CRAFTY_SHIELD("Crafty Shield") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            for (Pokemon pokemon : user.getTrainer().getActivePokemons()) {
                BaseVolatileStatus.CRAFTY_SHIELD.apply(user, pokemon, action);
            }

            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.raiseStatStage(Stat.SPECIAL_DEFENSE, 1);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    CROSS_CHOP("Cross Chop"),
    CROSS_POISON("Cross Poison"),
    CRUNCH("Crunch") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Generation generation = user.getBattle().getGeneration();
            double chance = Math.random();

            switch (generation) {
                case I:
                case II:
                case III:
                    if (chance < 0.2) {
                        target.lowerStatStage(Stat.SPECIAL_DEFENSE, 1);
                    }
                    break;
                case IV:
                case V:
                case VI:
                case VII:
                    if (chance < 0.2) {
                        target.lowerStatStage(Stat.DEFENSE, 1);
                    }
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            return super.use(user, target, battle, action);
        }
    },
    CRUSH_CLAW("Crush Claw"),
    CRUSH_GRIP("Crush Grip") {
        @Override
        public int getPower(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
            int targetCurrentHP = target.getCurrentStat(Stat.HP);
            int targetMaxHP = target.getStat(Stat.HP);

            switch (user.getBattle().getGeneration()) {
                case I:
                case II:
                case III:
                case IV:
                    return 1 + 120 * (targetCurrentHP / targetMaxHP);
                case V:
                case VI:
                case VII:
                    return 120 * (targetCurrentHP / targetMaxHP);
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }
        }
    },
    CURSE("Curse") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage = super.use(user, target, battle, action);

            if (user.isType(Type.GHOST)) {
                user.damage(50.0);
                BaseVolatileStatus.CURSE.apply(user, target, action);
            } else {
                user.lowerStatStage(Stat.SPEED, 1);
                user.raiseStatStage(Stat.ATTACK, 1);
                user.raiseStatStage(Stat.DEFENSE, 1);
            }

            return damage;
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (user.isType(Type.GHOST)) {
                user.heal(100.0);
            } else {
                user.raiseStatStage(Stat.ATTACK, 1);
            }

            return super.useAsZMove(user, target, battle, action);
        }

        @Override
        public boolean hits(Pokemon attacker, Pokemon defender, Battle battle, Move move) {
            if (defender.hasVolatileStatus(BaseVolatileStatus.CURSE)) {
                return false;
            }

            if (!defender.getTrainer().hasOpponentOnField()) {
                return false;
            }

            Generation generation = attacker.getBattle().getGeneration();
            switch (generation) {
                case I:
                case II:
                    if (defender.hasVolatileStatus(BaseVolatileStatus.SUBSTITUTE)) {
                        return false;
                    }
                    break;

                // If the user changes type before using Curse,
                // Curse will obey the user's type at the time of the move's execution.
                // In a Double Battle, if the user gains the Ghost type before Curse executes,
                // Curse will prioritize the opponent directly opposite the user as its target.
                case III:
                case IV:

                // In a Triple Battle, if the user gains the Ghost type before Curse executes,
                // Curse will prioritize the opponent directly opposite the user as its target.
                case V:
                    if (attacker.isType(Type.GHOST) && attacker == defender) {
                        attacker.retarget(attacker.getEnemyOppositePokemon());
                    }
                    break;
                case VI:
                case VII:
                    if (attacker.isType(Type.GHOST) && attacker == defender) {

                        Variation variation = attacker.getBattle().getVariation();
                        switch (variation) {
                            // In a Single Battle, if the user is not already Ghost-type and
                            // becomes Ghost-type before executing Curse (due to Mega Evolution,
                            // Color Change or Trick-or-Treat), Curse will execute as it does
                            // for all Ghost types, hurting the user and inflicting the curse.
                            case SINGLE_BATTLE:
                                attacker.retarget(attacker.getEnemyOppositePokemon());
                                break;

                            // In a Double Battle, if the user is not already Ghost-type and
                            // becomes Ghost-type before executing Curse (due to Mega Evolution,
                            // Color Change or Trick-or-Treat), Curse will now target the
                            // user's ally (or fail if there is none) if the user is on the
                            // right side (from its Trainer's perspective), or a random opponent
                            // if the user is on the left side (from its Trainer's perspective).
                            // Follow Me and Rage Powder will still redirect Curse as normal.
                            case DOUBLE_BATTLE:

                                // In a Double Battle, if the user is not already Ghost-type
                                // and becomes Ghost-type before executing Curse (due to Mega
                                // Evolution, Color Change or Trick-or-Treat), Curse will now
                                // target a random opponent.
                                if (generation == Generation.VII) {
                                    attacker.retarget(attacker.getRandomActiveEnemyPokemon());
                                    break;
                                }

                                if (attacker.getTrainer().getActivePokemon(attacker) == 1) {
                                    attacker.retarget(attacker.getTrainer().getActivePokemon(0));
                                } else {
                                    attacker.retarget(attacker.getRandomActiveEnemyPokemon());
                                }
                                break;

                            // In a Triple Battle or Horde Encounter, if the user is not already
                            // Ghost-type and becomes Ghost-type before executing Curse
                            // (due to Mega Evolution, Color Change or Trick-or-Treat), Curse
                            // will now target a random adjacent opponent.
                            case TRIPLE_BATTLE:
                                attacker.retarget(attacker.getRandomAdjacentEnemyPokemon());
                                break;
                            default:
                                throw new InvalidVariationException(variation);
                        }

                    }
                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }

            return super.hits(attacker, defender, battle, move);
        }
    },
    CUT("Cut"),
    DARK_PULSE("Dark Pulse"),
    DARK_VOID("Dark Void") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage = super.use(user, target, battle, action);

            List<Pokemon> adjacentEnemyPokemons = user.getTrainer().getAdjacentEnemyPokemons(user);

            Generation generation = battle.getGeneration();
            switch (generation) {
                case I:
                case II:
                case III:
                case IV:
                case V:
                case VI:
                    for (Pokemon adjacentEnemyPokemon : adjacentEnemyPokemons) {
                        if (Math.random() < 0.8) {
                            adjacentEnemyPokemon.setStatus(Status.SLEEP);
                        }
                    }
                    break;
                case VII:
                    for (Pokemon adjacentEnemyPokemon : adjacentEnemyPokemons) {
                        if (Math.random() < 0.5) {
                            adjacentEnemyPokemon.setStatus(Status.SLEEP);
                        }
                    }
                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }

            return damage;
        }

        @Override
        public boolean hits(Pokemon attacker, Pokemon defender, Battle battle, Move move) {
            if (battle.getGeneration() == Generation.VII) {
                if (attacker.getBasePokemon() != Pokemons.DARKRAI) {
                    return false;
                }
            }

            return super.hits(attacker, defender, battle, move);
        }
    },
    DARKEST_LARIAT("Darkest Lariat") {
        @Override
        protected int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, Move move) {
            if (getCategory() == Category.OTHER) {
                return 0;
            }

            int attackStat;
            int defenseStat;
            int level = attacker.getLevel();
            int attackPower = this.POWER;
            double stabMultiplier = attacker.getStabMultiplier(move);
            double effectiveness = Type.getDamageMultiplier(defender.getTypes(), getType());
            double randomNumber = ThreadLocalRandom.current().nextDouble(0.85, 1.0);

            if (getCategory() == Category.PHYSICAL) {
                attackStat = attacker.getStat(Stat.ATTACK);
                defenseStat = defender.getStatWithoutStages(Stat.DEFENSE);
            } else if (getCategory() == Category.SPECIAL) {
                attackStat = attacker.getStat(Stat.SPECIAL_ATTACK);
                defenseStat = defender.getStatWithoutStages(Stat.SPECIAL_DEFENSE);
            } else {
                throw new InvalidCategoryException(getCategory());
            }

            return (int) (((((2 * level / 5 + 2) * attackStat * attackPower / defenseStat) / 50) + 2) * stabMultiplier * effectiveness * randomNumber);
        }

        @Override
        public boolean hits(Pokemon attacker, Pokemon defender, Battle battle, Move move) {
            if (defender.isImmune(move)) {
                return false;
            }

            if (getAccuracy() == 0) {
                return true;
            }

            if (attacker.getBattle().getGeneration() == Generation.I) {
                if (Math.random() < 0.004) {
                    return false;
                }
            }

            double probability = getAccuracy() * (attacker.getAccuracy() / defender.getEvasionWithoutStatChanges());
            if (probability > 1 || probability < Math.random()) {
                return true;
            }

            return false;
        }
    },
    DAZZLING_GLEAM("Dazzling Gleam"),
    DEFEND_ORDER("Defend Order"),
    DEFENSE_CURL("Defense Curl"),
    DEFOG("Defog"),
    DESTINY_BOND("Destiny Bond"),
    DETECT("Detect"),
    DEVASTATING_DRAKE("Devastating Drake"),
    DIAMOND_STORM("Diamond Storm"),
    DIG("Dig"),
    DISABLE("Disable"),
    DISARMING_VOICE("Disarming Voice"),
    DISCHARGE("Discharge"),
    DIVE("Dive"),
    DIZZY_PUNCH("Dizzy Punch"),
    DOOM_DESIRE("Doom Desire"),
    DOUBLE_HIT("Double Hit"),
    DOUBLE_KICK("Double Kick"),
    DOUBLE_SLAP("Double Slap"),
    DOUBLE_TEAM("Double Team"),
    DOUBLE_EDGE("Double-Edge"),
    DRACO_METEOR("Draco Meteor"),
    DRAGON_ASCENT("Dragon Ascent"),
    DRAGON_BREATH("Dragon Breath"),
    DRAGON_CLAW("Dragon Claw"),
    DRAGON_DANCE("Dragon Dance"),
    DRAGON_HAMMER("Dragon Hammer"),
    DRAGON_PULSE("Dragon Pulse"),
    DRAGON_RAGE("Dragon Rage"),
    DRAGON_RUSH("Dragon Rush"),
    DRAGON_TAIL("Dragon Tail"),
    DRAIN_PUNCH("Drain Punch"),
    DRAINING_KISS("Draining Kiss"),
    DREAM_EATER("Dream Eater"),
    DRILL_PECK("Drill Peck"),
    DRILL_RUN("Drill Run"),
    DUAL_CHOP("Dual Chop"),
    DYNAMIC_PUNCH("Dynamic Punch"),
    EARTH_POWER("Earth Power"),
    EARTHQUAKE("Earthquake"),
    ECHOED_VOICE("Echoed Voice"),
    EERIE_IMPULSE("Eerie Impulse"),
    EGG_BOMB("Egg Bomb"),
    ELECTRIC_TERRAIN("Electric Terrain"),
    ELECTRIFY("Electrify"),
    ELECTRO_BALL("Electro Ball"),
    ELECTROWEB("Electroweb"),
    EMBARGO("Embargo"),
    EMBER("Ember"),
    ENCORE("Encore"),
    ENDEAVOR("Endeavor"),
    ENDURE("Endure"),
    ENERGY_BALL("Energy Ball"),
    ENTRAINMENT("Entrainment"),
    ERUPTION("Eruption"),
    EXPLOSION("Explosion"),
    EXTRASENSORY("Extrasensory"),
    EXTREME_EVOBOOST("Extreme Evoboost"),
    EXTREME_SPEED("Extreme Speed"),
    FACADE("Facade"),
    FAIRY_LOCK("Fairy Lock"),
    FAIRY_WIND("Fairy Wind"),
    FAKE_OUT("Fake Out"),
    FAKE_TEARS("Fake Tears"),
    FALSE_SWIPE("False Swipe"),
    FEATHER_DANCE("Feather Dance"),
    FEINT("Feint"),
    FEINT_ATTACK("Feint Attack"),
    FELL_STINGER("Fell Stinger"),
    FIERY_DANCE("Fiery Dance"),
    FINAL_GAMBIT("Final Gambit"),
    FIRE_BLAST("Fire Blast"),
    FIRE_FANG("Fire Fang"),
    FIRE_LASH("Fire Lash"),
    FIRE_PLEDGE("Fire Pledge"),
    FIRE_PUNCH("Fire Punch"),
    FIRE_SPIN("Fire Spin"),
    FIRST_IMPRESSION("First Impression"),
    FISSURE("Fissure"),
    FLAIL("Flail"),
    FLAME_BURST("Flame Burst"),
    FLAME_CHARGE("Flame Charge"),
    FLAME_WHEEL("Flame Wheel") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            switch (battle.getGeneration()) {
                case I:
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    if (user.getStatus() == Status.FREEZE) {
                        Status.FREEZE.remove(user);
                    }
                    break;
            }

            return super.use(user, target, battle, action);
        }
    },
    FLAMETHROWER("Flamethrower"),
    FLARE_BLITZ("Flare Blitz") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            switch (battle.getGeneration()) {
                case I:
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    if (user.getStatus() == Status.FREEZE) {
                        Status.FREEZE.remove(user);
                    }
                    break;
            }

            return super.use(user, target, battle, action);
        }
    },
    FLASH("Flash"),
    FLASH_CANNON("Flash Cannon"),
    FLATTER("Flatter"),
    FLEUR_CANNON("Fleur Cannon"),
    FLING("Fling"),
    FLORAL_HEALING("Floral Healing"),
    FLOWER_SHIELD("Flower Shield"),
    FLY("Fly"),
    FLYING_PRESS("Flying Press"),
    FOCUS_BLAST("Focus Blast"),
    FOCUS_ENERGY("Focus Energy"),
    FOCUS_PUNCH("Focus Punch"),
    FOLLOW_ME("Follow Me"),
    FORCE_PALM("Force Palm"),
    FORESIGHT("Foresight"),
    FORESTS_CURSE("Forest's Curse"),
    FOUL_PLAY("Foul Play"),
    FREEZE_SHOCK("Freeze Shock"),
    FREEZE_DRY("Freeze-Dry"),
    FRENZY_PLANT("Frenzy Plant"),
    FROST_BREATH("Frost Breath"),
    FRUSTRATION("Frustration"),
    FURY_ATTACK("Fury Attack"),
    FURY_CUTTER("Fury Cutter"),
    FURY_SWIPES("Fury Swipes"),
    FUSION_BOLT("Fusion Bolt"),
    FUSION_FLARE("Fusion Flare") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            switch (battle.getGeneration()) {
                case I:
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    if (user.getStatus() == Status.FREEZE) {
                        Status.FREEZE.remove(user);
                    }
                    break;
            }

            return super.use(user, target, battle, action);
        }
    },
    FUTURE_SIGHT("Future Sight"),
    GASTRO_ACID("Gastro Acid"),
    GEAR_GRIND("Gear Grind"),
    GEAR_UP("Gear Up"),
    GENESIS_SUPERNOVA("Genesis Supernova"),
    GEOMANCY("Geomancy"),
    GIGA_DRAIN("Giga Drain"),
    GIGA_IMPACT("Giga Impact"),
    GIGAVOLT_HAVOC("Gigavolt Havoc"),
    GLACIATE("Glaciate"),
    GLARE("Glare"),
    GRASS_KNOT("Grass Knot"),
    GRASS_PLEDGE("Grass Pledge"),
    GRASS_WHISTLE("Grass Whistle"),
    GRASSY_TERRAIN("Grassy Terrain"),
    GRAVITY("Gravity"),
    GROWL("Growl"),
    GROWTH("Growth"),
    GRUDGE("Grudge"),
    GUARD_SPLIT("Guard Split"),
    GUARD_SWAP("Guard Swap"),
    GUARDIAN_OF_ALOLA("Guardian of Alola"),
    GUILLOTINE("Guillotine"),
    GUNK_SHOT("Gunk Shot"),
    GUST("Gust"),
    GYRO_BALL("Gyro Ball"),
    HAIL("Hail"),
    HAMMER_ARM("Hammer Arm"),
    HAPPY_HOUR("Happy Hour"),
    HARDEN("Harden"),
    HAZE("Haze") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage =  super.use(user, target, battle, action);

            switch (battle.getGeneration()) {
                case I:
                    for (Trainer battleTrainer : battle.getTrainers().values()) {
                        for (Pokemon pokemon : battleTrainer.getActivePokemons()) {
                            pokemon.resetStatStages();
                            pokemon.removeStatModifier(Status.BURN, Status.PARALYSIS);
//                            pokemon.removeVolatileStatus(); // TODO: Removes effects of focus energy, dire hit, mist, guard spec, x accuracy, leech sed, disable, reflect, light screen
                            pokemon.removeVolatileStatus(BaseVolatileStatus.CONFUSION);
                            if (pokemon.getStatus() == Status.BADLY_POISONED) {
                                pokemon.setStatus(Status.POISON);
                            }
                            if (pokemon != user) {
                                pokemon.resetStatus();
                            }
                        }
                    }
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    for (Trainer battleTrainer : battle.getTrainers().values()) {
                        for (Pokemon pokemon : battleTrainer.getActivePokemons()) {
                            if (battle.getGeneration() == Generation.III && pokemon.hasVolatileStatus(BaseVolatileStatus.PROTECTION)) {
                                this.miss(user);
                            }
                            pokemon.resetStatStages();
                        }
                    }
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            return damage;
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.heal(100.0);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    HEAD_CHARGE("Head Charge"),
    HEAD_SMASH("Head Smash"),
    HEADBUTT("Headbutt"),
    HEAL_BELL("Heal Bell"),
    HEAL_BLOCK("Heal Block"),
    HEAL_ORDER("Heal Order"),
    HEAL_PULSE("Heal Pulse"),
    HEALING_WISH("Healing Wish"),
    HEART_STAMP("Heart Stamp"),
    HEART_SWAP("Heart Swap"),
    HEAT_CRASH("Heat Crash"),
    HEAT_WAVE("Heat Wave"),
    HEAVY_SLAM("Heavy Slam"),
    HELPING_HAND("Helping Hand"),
    HEX("Hex"),
    HIDDEN_POWER("Hidden Power"),
    HIGH_HORSEPOWER("High Horsepower"),
    HIGH_JUMP_KICK("High Jump Kick"),
    HOLD_BACK("Hold Back"),
    HOLD_HANDS("Hold Hands"),
    HONE_CLAWS("Hone Claws"),
    HORN_ATTACK("Horn Attack"),
    HORN_DRILL("Horn Drill"),
    HORN_LEECH("Horn Leech"),
    HOWL("Howl"),
    HURRICANE("Hurricane"),
    HYDRO_CANNON("Hydro Cannon"),
    HYDRO_PUMP("Hydro Pump"),
    HYDRO_VORTEX("Hydro Vortex"),
    HYPER_BEAM("Hyper Beam"),
    HYPER_FANG("Hyper Fang"),
    HYPER_VOICE("Hyper Voice"),
    HYPERSPACE_FURY("Hyperspace Fury"),
    HYPERSPACE_HOLE("Hyperspace Hole"),
    HYPNOSIS("Hypnosis"),
    ICE_BALL("Ice Ball"),
    ICE_BEAM("Ice Beam"),
    ICE_BURN("Ice Burn"),
    ICE_FANG("Ice Fang"),
    ICE_HAMMER("Ice Hammer"),
    ICE_PUNCH("Ice Punch"),
    ICE_SHARD("Ice Shard"),
    ICICLE_CRASH("Icicle Crash"),
    ICICLE_SPEAR("Icicle Spear"),
    ICY_WIND("Icy Wind"),
    IMPRISON("Imprison"),
    INCINERATE("Incinerate"),
    INFERNO("Inferno"),
    INFERNO_OVERDRIVE("Inferno Overdrive"),
    INFESTATION("Infestation"),
    INGRAIN("Ingrain"),
    INSTRUCT("Instruct"),
    ION_DELUGE("Ion Deluge"),
    IRON_DEFENSE("Iron Defense"),
    IRON_HEAD("Iron Head"),
    IRON_TAIL("Iron Tail"),
    JUDGMENT("Judgment"),
    JUMP_KICK("Jump Kick"),
    KARATE_CHOP("Karate Chop"),
    KINESIS("Kinesis"),
    KINGS_SHIELD("King's Shield"),
    KNOCK_OFF("Knock Off"),
    LANDS_WRATH("Land's Wrath"),
    LASER_FOCUS("Laser Focus"),
    LAST_RESORT("Last Resort"),
    LAVA_PLUME("Lava Plume"),
    LEAF_BLADE("Leaf Blade"),
    LEAF_STORM("Leaf Storm"),
    LEAF_TORNADO("Leaf Tornado"),
    LEAFAGE("Leafage"),
    LEECH_LIFE("Leech Life"),
    LEECH_SEED("Leech Seed"),
    LEER("Leer"),
    LICK("Lick"),
    LIGHT_SCREEN("Light Screen"),
    LIGHT_OF_RUIN("Light of Ruin"),
    LIQUIDATION("Liquidation"),
    LOCK_ON("Lock-On"),
    LOVELY_KISS("Lovely Kiss"),
    LOW_KICK("Low Kick"),
    LOW_SWEEP("Low Sweep"),
    LUCKY_CHANT("Lucky Chant"),
    LUNAR_DANCE("Lunar Dance"),
    LUNGE("Lunge"),
    LUSTER_PURGE("Luster Purge"),
    MACH_PUNCH("Mach Punch"),
    MAGIC_COAT("Magic Coat"),
    MAGIC_ROOM("Magic Room"),
    MAGICAL_LEAF("Magical Leaf"),
    MAGMA_STORM("Magma Storm"),
    MAGNET_BOMB("Magnet Bomb"),
    MAGNET_RISE("Magnet Rise"),
    MAGNETIC_FLUX("Magnetic Flux"),
    MAGNITUDE("Magnitude"),
    MALICIOUS_MOONSAULT("Malicious Moonsault"),
    MAT_BLOCK("Mat Block"),
    ME_FIRST("Me First"),
    MEAN_LOOK("Mean Look"),
    MEDITATE("Meditate"),
    MEGA_DRAIN("Mega Drain"),
    MEGA_KICK("Mega Kick"),
    MEGA_PUNCH("Mega Punch"),
    MEGAHORN("Megahorn"),
    MEMENTO("Memento"),
    METAL_BURST("Metal Burst"),
    METAL_CLAW("Metal Claw"),
    METAL_SOUND("Metal Sound"),
    METEOR_MASH("Meteor Mash"),
    METRONOME("Metronome"),
    MILK_DRINK("Milk Drink"),
    MIMIC("Mimic"),
    MIND_READER("Mind Reader"),
    MINIMIZE("Minimize"),
    MIRACLE_EYE("Miracle Eye"),
    MIRROR_COAT("Mirror Coat"),
    MIRROR_MOVE("Mirror Move"),
    MIRROR_SHOT("Mirror Shot"),
    MIST("Mist"),
    MIST_BALL("Mist Ball"),
    MISTY_TERRAIN("Misty Terrain"),
    MOONBLAST("Moonblast"),
    MOONGEIST_BEAM("Moongeist Beam"),
    MOONLIGHT("Moonlight"),
    MORNING_SUN("Morning Sun"),
    MUD_BOMB("Mud Bomb"),
    MUD_SHOT("Mud Shot"),
    MUD_SPORT("Mud Sport"),
    MUD_SLAP("Mud-Slap"),
    MUDDY_WATER("Muddy Water"),
    MULTI_ATTACK("Multi-Attack"),
    MYSTICAL_FIRE("Mystical Fire"),
    NASTY_PLOT("Nasty Plot"),
    NATURAL_GIFT("Natural Gift"),
    NATURE_POWER("Nature Power"),
    NATURES_MADNESS("Nature's Madness"),
    NEEDLE_ARM("Needle Arm"),
    NEVER_ENDING_NIGHTMARE("Never-Ending Nightmare"),
    NIGHT_DAZE("Night Daze"),
    NIGHT_SHADE("Night Shade"),
    NIGHT_SLASH("Night Slash"),
    NIGHTMARE("Nightmare"),
    NOBLE_ROAR("Noble Roar"),
    NUZZLE("Nuzzle"),
    OBLIVION_WING("Oblivion Wing"),
    OCEANIC_OPERETTA("Oceanic Operetta"),
    OCTAZOOKA("Octazooka"),
    ODOR_SLEUTH("Odor Sleuth"),
    OMINOUS_WIND("Ominous Wind"),
    ORIGIN_PULSE("Origin Pulse"),
    OUTRAGE("Outrage"),
    OVERHEAT("Overheat"),
    PAIN_SPLIT("Pain Split"),
    PARABOLIC_CHARGE("Parabolic Charge"),
    PARTING_SHOT("Parting Shot"),
    PAY_DAY("Pay Day"),
    PAYBACK("Payback"),
    PECK("Peck"),
    PERISH_SONG("Perish Song"),
    PETAL_BLIZZARD("Petal Blizzard"),
    PETAL_DANCE("Petal Dance"),
    PHANTOM_FORCE("Phantom Force"),
    PIN_MISSILE("Pin Missile"),
    PLAY_NICE("Play Nice"),
    PLAY_ROUGH("Play Rough"),
    PLUCK("Pluck"),
    POISON_FANG("Poison Fang"),
    POISON_GAS("Poison Gas"),
    POISON_JAB("Poison Jab"),
    POISON_POWDER("Poison Powder"),
    POISON_STING("Poison Sting"),
    POISON_TAIL("Poison Tail"),
    POLLEN_PUFF("Pollen Puff"),
    POUND("Pound"),
    POWDER("Powder"),
    POWDER_SNOW("Powder Snow"),
    POWER_GEM("Power Gem"),
    POWER_SPLIT("Power Split"),
    POWER_SWAP("Power Swap"),
    POWER_TRICK("Power Trick"),
    POWER_TRIP("Power Trip"),
    POWER_WHIP("Power Whip"),
    POWER_UP_PUNCH("Power-Up Punch"),
    PRECIPICE_BLADES("Precipice Blades"),
    PRESENT("Present"),
    PRISMATIC_LASER("Prismatic Laser"),
    PROTECT("Protect"),
    PSYBEAM("Psybeam"),
    PSYCH_UP("Psych Up"),
    PSYCHIC("Psychic"),
    PSYCHIC_FANGS("Psychic Fangs"),
    PSYCHIC_TERRAIN("Psychic Terrain"),
    PSYCHO_BOOST("Psycho Boost"),
    PSYCHO_CUT("Psycho Cut"),
    PSYCHO_SHIFT("Psycho Shift"),
    PSYSHOCK("Psyshock"),
    PSYSTRIKE("Psystrike"),
    PSYWAVE("Psywave"),
    PULVERIZING_PANCAKE("Pulverizing Pancake"),
    PUNISHMENT("Punishment"),
    PURIFY("Purify"),
    PURSUIT("Pursuit"),
    QUASH("Quash"),
    QUICK_ATTACK("Quick Attack"),
    QUICK_GUARD("Quick Guard"),
    QUIVER_DANCE("Quiver Dance"),
    RAGE("Rage"),
    RAGE_POWDER("Rage Powder"),
    RAIN_DANCE("Rain Dance"),
    RAPID_SPIN("Rapid Spin"),
    RAZOR_LEAF("Razor Leaf"),
    RAZOR_SHELL("Razor Shell"),
    RAZOR_WIND("Razor Wind"),
    RECOVER("Recover"),
    RECYCLE("Recycle"),
    REFLECT("Reflect"),
    REFLECT_TYPE("Reflect Type"),
    REFRESH("Refresh"),
    RELIC_SONG("Relic Song"),
    REST("Rest"),
    RETALIATE("Retaliate"),
    RETURN("Return"),
    REVELATION_DANCE("Revelation Dance"),
    REVENGE("Revenge"),
    REVERSAL("Reversal"),
    ROAR("Roar"),
    ROAR_OF_TIME("Roar of Time"),
    ROCK_BLAST("Rock Blast"),
    ROCK_CLIMB("Rock Climb"),
    ROCK_POLISH("Rock Polish"),
    ROCK_SLIDE("Rock Slide"),
    ROCK_SMASH("Rock Smash"),
    ROCK_THROW("Rock Throw"),
    ROCK_TOMB("Rock Tomb"),
    ROCK_WRECKER("Rock Wrecker"),
    ROLE_PLAY("Role Play"),
    ROLLING_KICK("Rolling Kick"),
    ROLLOUT("Rollout"),
    ROOST("Roost"),
    ROTOTILLER("Rototiller"),
    ROUND("Round"),
    SACRED_FIRE("Sacred Fire") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            switch (battle.getGeneration()) {
                case I:
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    if (user.getStatus() == Status.FREEZE) {
                        Status.FREEZE.remove(user);
                    }
                    break;
            }

            return super.use(user, target, battle, action);
        }
    },
    SACRED_SWORD("Sacred Sword"),
    SAFEGUARD("Safeguard"),
    SAND_ATTACK("Sand Attack"),
    SAND_TOMB("Sand Tomb"),
    SANDSTORM("Sandstorm"),
    SAVAGE_SPIN_OUT("Savage Spin-Out"),
    SCALD("Scald") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            switch (battle.getGeneration()) {
                case I:
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    if (user.getStatus() == Status.FREEZE) {
                        Status.FREEZE.remove(user);
                    }
                    break;
            }

            return super.use(user, target, battle, action);
        }
    },
    SCARY_FACE("Scary Face"),
    SCRATCH("Scratch"),
    SCREECH("Screech"),
    SEARING_SHOT("Searing Shot"),
    SECRET_POWER("Secret Power"),
    SECRET_SWORD("Secret Sword"),
    SEED_BOMB("Seed Bomb"),
    SEED_FLARE("Seed Flare"),
    SEISMIC_TOSS("Seismic Toss"),
    SELF_DESTRUCT("Self-Destruct"),
    SHADOW_BALL("Shadow Ball"),
    SHADOW_BONE("Shadow Bone"),
    SHADOW_CLAW("Shadow Claw"),
    SHADOW_FORCE("Shadow Force"),
    SHADOW_PUNCH("Shadow Punch"),
    SHADOW_SNEAK("Shadow Sneak"),
    SHARPEN("Sharpen"),
    SHATTERED_PSYCHE("Shattered Psyche"),
    SHEER_COLD("Sheer Cold"),
    SHELL_SMASH("Shell Smash"),
    SHELL_TRAP("Shell Trap"),
    SHIFT_GEAR("Shift Gear"),
    SHOCK_WAVE("Shock Wave"),
    SHORE_UP("Shore Up"),
    SIGNAL_BEAM("Signal Beam"),
    SILVER_WIND("Silver Wind"),
    SIMPLE_BEAM("Simple Beam"),
    SING("Sing"),
    SINISTER_ARROW_RAID("Sinister Arrow Raid"),
    SKETCH("Sketch"),
    SKILL_SWAP("Skill Swap"),
    SKULL_BASH("Skull Bash"),
    SKY_ATTACK("Sky Attack"),
    SKY_DROP("Sky Drop"),
    SKY_UPPERCUT("Sky Uppercut"),
    SLACK_OFF("Slack Off"),
    SLAM("Slam"),
    SLASH("Slash"),
    SLEEP_POWDER("Sleep Powder"),
    SLEEP_TALK("Sleep Talk"),
    SLUDGE("Sludge"),
    SLUDGE_BOMB("Sludge Bomb"),
    SLUDGE_WAVE("Sludge Wave"),
    SMACK_DOWN("Smack Down"),
    SMART_STRIKE("Smart Strike"),
    SMELLING_SALTS("Smelling Salts"),
    SMOG("Smog"),
    SMOKESCREEN("Smokescreen"),
    SNARL("Snarl"),
    SNATCH("Snatch"),
    SNORE("Snore"),
    SOAK("Soak"),
    SOFT_BOILED("Soft-Boiled"),
    SOLAR_BEAM("Solar Beam"),
    SOLAR_BLADE("Solar Blade"),
    SONIC_BOOM("Sonic Boom"),
    SOUL_STEALING_7_STAR_STRIKE("Soul-Stealing 7-Star Strike"),
    SPACIAL_REND("Spacial Rend"),
    SPARK("Spark"),
    SPARKLING_ARIA("Sparkling Aria"),
    SPECTRAL_THIEF("Spectral Thief"),
    SPEED_SWAP("Speed Swap"),
    SPIDER_WEB("Spider Web"),
    SPIKE_CANNON("Spike Cannon"),
    SPIKES("Spikes"),
    SPIKY_SHIELD("Spiky Shield"),
    SPIRIT_SHACKLE("Spirit Shackle"),
    SPIT_UP("Spit Up"),
    SPITE("Spite"),
    SPLASH("Splash"),
    SPORE("Spore"),
    SPOTLIGHT("Spotlight"),
    STEALTH_ROCK("Stealth Rock"),
    STEAM_ERUPTION("Steam Eruption") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            switch (battle.getGeneration()) {
                case I:
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    if (user.getStatus() == Status.FREEZE) {
                        Status.FREEZE.remove(user);
                    }
                    break;
            }

            return super.use(user, target, battle, action);
        }
    },
    STEAMROLLER("Steamroller"),
    STEEL_WING("Steel Wing"),
    STICKY_WEB("Sticky Web"),
    STOCKPILE("Stockpile"),
    STOKED_SPARKSURFER("Stoked Sparksurfer"),
    STOMP("Stomp"),
    STOMPING_TANTRUM("Stomping Tantrum"),
    STONE_EDGE("Stone Edge"),
    STORED_POWER("Stored Power"),
    STORM_THROW("Storm Throw"),
    STRENGTH("Strength"),
    STRENGTH_SAP("Strength Sap"),
    STRING_SHOT("String Shot"),
    STRUGGLE("Struggle"),
    STRUGGLE_BUG("Struggle Bug"),
    STUN_SPORE("Stun Spore"),
    SUBMISSION("Submission"),
    SUBSTITUTE("Substitute"),
    SUBZERO_SLAMMER("Subzero Slammer"),
    SUCKER_PUNCH("Sucker Punch"),
    SUNNY_DAY("Sunny Day"),
    SUNSTEEL_STRIKE("Sunsteel Strike"),
    SUPER_FANG("Super Fang"),
    SUPERPOWER("Superpower"),
    SUPERSONIC("Supersonic"),
    SUPERSONIC_SKYSTRIKE("Supersonic Skystrike"),
    SURF("Surf"),
    SWAGGER("Swagger"),
    SWALLOW("Swallow"),
    SWEET_KISS("Sweet Kiss"),
    SWEET_SCENT("Sweet Scent"),
    SWIFT("Swift"),
    SWITCHEROO("Switcheroo"),
    SWORDS_DANCE("Swords Dance"),
    SYNCHRONOISE("Synchronoise"),
    SYNTHESIS("Synthesis"),
    TACKLE("Tackle"),
    TAIL_GLOW("Tail Glow"),
    TAIL_SLAP("Tail Slap"),
    TAIL_WHIP("Tail Whip"),
    TAILWIND("Tailwind"),
    TAKE_DOWN("Take Down"),
    TAUNT("Taunt"),
    TEARFUL_LOOK("Tearful Look"),
    TECHNO_BLAST("Techno Blast"),
    TECTONIC_RAGE("Tectonic Rage"),
    TEETER_DANCE("Teeter Dance"),
    TELEKINESIS("Telekinesis"),
    TELEPORT("Teleport"),
    THIEF("Thief"),
    THOUSAND_ARROWS("Thousand Arrows"),
    THOUSAND_WAVES("Thousand Waves"),
    THRASH("Thrash"),
    THROAT_CHOP("Throat Chop"),
    THUNDER("Thunder"),
    THUNDER_FANG("Thunder Fang"),
    THUNDER_PUNCH("Thunder Punch"),
    THUNDER_SHOCK("Thunder Shock"),
    THUNDER_WAVE("Thunder Wave"),
    THUNDERBOLT("Thunderbolt"),
    TICKLE("Tickle"),
    TOPSY_TURVY("Topsy-Turvy"),
    TORMENT("Torment"),
    TOXIC("Toxic"),
    TOXIC_SPIKES("Toxic Spikes"),
    TOXIC_THREAD("Toxic Thread"),
    TRANSFORM("Transform"),
    TRI_ATTACK("Tri Attack") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (battle.getGeneration() == Generation.II) {
                if (target.getStatus() == Status.FREEZE && Math.random() < 0.3) {
                    Status.FREEZE.remove(user);
                }
            }

            return super.use(user, target, battle, action);
        }
    },
    TRICK("Trick"),
    TRICK_ROOM("Trick Room"),
    TRICK_OR_TREAT("Trick-or-Treat"),
    TRIPLE_KICK("Triple Kick"),
    TROP_KICK("Trop Kick"),
    TRUMP_CARD("Trump Card"),
    TWINEEDLE("Twineedle"),
    TWINKLE_TACKLE("Twinkle Tackle"),
    TWISTER("Twister"),
    U_TURN("U-turn"),
    UPROAR("Uproar"),
    V_CREATE("V-create"),
    VACUUM_WAVE("Vacuum Wave"),
    VENOM_DRENCH("Venom Drench"),
    VENOSHOCK("Venoshock"),
    VICE_GRIP("Vice Grip"),
    VINE_WHIP("Vine Whip"),
    VITAL_THROW("Vital Throw"),
    VOLT_SWITCH("Volt Switch"),
    VOLT_TACKLE("Volt Tackle"),
    WAKE_UP_SLAP("Wake-Up Slap"),
    WATER_GUN("Water Gun"),
    WATER_PLEDGE("Water Pledge"),
    WATER_PULSE("Water Pulse"),
    WATER_SHURIKEN("Water Shuriken"),
    WATER_SPORT("Water Sport"),
    WATER_SPOUT("Water Spout"),
    WATERFALL("Waterfall"),
    WEATHER_BALL("Weather Ball"),
    WHIRLPOOL("Whirlpool"),
    WHIRLWIND("Whirlwind"),
    WIDE_GUARD("Wide Guard"),
    WILD_CHARGE("Wild Charge"),
    WILL_O_WISP("Will-O-Wisp"),
    WING_ATTACK("Wing Attack"),
    WISH("Wish"),
    WITHDRAW("Withdraw"),
    WONDER_ROOM("Wonder Room"),
    WOOD_HAMMER("Wood Hammer"),
    WORK_UP("Work Up"),
    WORRY_SEED("Worry Seed"),
    WRAP("Wrap"),
    WRING_OUT("Wring Out"),
    X_SCISSOR("X-Scissor"),
    YAWN("Yawn"),
    ZAP_CANNON("Zap Cannon"),
    ZEN_HEADBUTT("Zen Headbutt"),
    ZING_ZAP("Zing Zap");

    private final String NAME;
    private final List<Status> STATUS_EFFECTS = new ArrayList<>();
    private final Map<BaseVolatileStatus, Integer> VOLATILE_STATUS_EFFECTS_CHANCE = new HashMap<>();
    private final Map<Stat, Integer> RAISES_OWN_STATS = new HashMap<>();
    private final Map<Stat, Integer> LOWERS_OWN_STATS = new HashMap<>();
    private final Map<Stat, Integer> RAISES_ENEMY_STATS = new HashMap<>();
    private final Map<Stat, Integer> LOWERS_ENEMY_STATS = new HashMap<>();
    private final Map<Stat, Integer> RAISES_TARGET_STATS = new HashMap<>();
    private final Map<Stat, Integer> LOWERS_TARGET_STATS = new HashMap<>();
    protected int POWER;
    protected Integer EFFECT_RATE = null;
    private Type TYPE;
    private Category CATEGORY;
    private MoveEffect EFFECT;
    private int PP;
    private int ACCURACY;
    private boolean IS_Z_MOVE = false;
    private final List<String> Z_MOVE_REQUIRED_POKEMON = new ArrayList<>();
    private BaseMove Z_MOVE_REQUIRED_MOVE = null;
    private final List<BaseMove> Z_MOVE_MOVES_THAT_TURN_INTO_THIS = new ArrayList<>();
    private boolean IS_SELF_Z_MOVE = false;
    private Item Z_MOVE_ITEM;
    private String BATTLE_EFFECT;
    private String IN_DEPTH_EFFECT;
    private String SECONDARY_EFFECT;
    private Integer RAISES_OWN_RANDOM_STAT = null;
    private Integer LOWERS_OWN_RANDOM_STAT = null;
    private Integer RAISES_ENEMY_RANDOM_STAT = null;
    private Integer LOWERS_ENEMY_RANDOM_STAT = null;
    private Integer RAISES_TARGET_RANDOM_STAT = null;
    private Integer LOWERS_TARGET_RANDOM_STAT = null;
    private BaseMove CORRESPONDING_Z_MOVE;
    private Integer Z_MOVE_POWER;
    private String DETAILED_EFFECT;
    private CriticalHitStage BASE_CRITICAL_HIT_RATE;
    private int PRIORITY;
    private Target TARGET;
    private Hit POKEMON_HIT;
    private boolean PHYSICAL_CONTACT;
    private boolean SOUND_TYPE;
    private boolean PUNCH_MOVE;
    private boolean SNATCHABLE;
    private boolean Z_MOVE;
    private boolean DEFROSTS_WHEN_USED;
    private boolean HITS_OPPOSITE_SIDE_IN_TRIPLES;
    private boolean REFLECTED;
    private boolean BLOCKED;
    private boolean COPYABLE;

    BaseMove(@Nonnull String name) {
        Holder.MAP.put(name.toLowerCase(), this);
        this.NAME = name;
    }

    public static BaseMove getMove(@Nonnull String move) {
        move = move.toLowerCase();
        if (move.startsWith("hidden power")) {
            return Holder.MAP.get("hidden power");
        }
        if (!Holder.MAP.containsKey(move)) {
            throw new NullPointerException("Move " + move + " doesn't exist");
        }

        return Holder.MAP.get(move);
    }

    protected BaseMove setType(@Nonnull Type type) {
        this.TYPE = type;
        return this;
    }

    protected BaseMove setCategory(@Nonnull Category category) {
        this.CATEGORY = category;
        return this;
    }

    protected BaseMove setEffect(@Nonnull MoveEffect effect) {
        this.EFFECT = effect;
        return this;
    }

    protected BaseMove setPP(int pp) {
        this.PP = pp;
        return this;
    }

    protected BaseMove setPower(int power) {
        this.POWER = power;
        return this;
    }

    protected BaseMove setAccuracy(int accuracy) {
        this.ACCURACY = accuracy;
        return this;
    }

    protected BaseMove setIsZMove(boolean bool) {
        this.IS_Z_MOVE = bool;
        return this;
    }

    protected BaseMove setIsSelfZMove(boolean bool) {
        this.IS_SELF_Z_MOVE = bool;
        return this;
    }

    protected BaseMove setZMoveItem(@Nullable Item item) {
        this.Z_MOVE_ITEM = item;
        return this;
    }

    protected BaseMove setZMoveRequiredPokemon(@Nullable String... pokemon) {
        if (pokemon != null) {
            this.Z_MOVE_REQUIRED_POKEMON.addAll(Arrays.asList(pokemon));
        }
        return this;
    }

    protected BaseMove setZMoveRequiredMove(@Nullable BaseMove move) {
        this.Z_MOVE_REQUIRED_MOVE = move;
        return this;
    }

    protected BaseMove setZMoveMovesThatTurnIntoThis(@Nullable BaseMove move) {
        if (move != null) {
            this.Z_MOVE_MOVES_THAT_TURN_INTO_THIS.add(move);
        }
        return this;
    }

    protected BaseMove setZMoveMovesThatTurnIntoThis(@Nullable String move) {
        if (move != null) {
            this.setZMoveMovesThatTurnIntoThis(BaseMove.getMove(move));
        }
        return this;
    }

    protected BaseMove setZMoveMovesThatTurnIntoThis(@Nullable BaseMove... moves) {
        if (moves != null) {
            this.Z_MOVE_MOVES_THAT_TURN_INTO_THIS.addAll(Arrays.asList(moves));
        }
        return this;
    }

    protected BaseMove setZMoveMovesThatTurnIntoThis(@Nullable String... moves) {
        if (moves != null) {
            for (String move : moves) {
                this.setZMoveMovesThatTurnIntoThis(move);
            }
        }
        return this;
    }

    protected BaseMove setBattleEffect(@Nonnull String battleEffect) {
        this.BATTLE_EFFECT = battleEffect;
        return this;
    }

    protected BaseMove setInDepthEffect(@Nullable String inDepthEffect) {
        this.IN_DEPTH_EFFECT = inDepthEffect;
        return this;
    }

    protected BaseMove setSecondaryEffect(@Nonnull String secondaryEffect) {
        this.SECONDARY_EFFECT = secondaryEffect;
        return this;
    }

    protected BaseMove setEffectRate(@Nullable Integer effectRate) {
        this.EFFECT_RATE = effectRate;
        return this;
    }

    protected BaseMove setStatusEffects(@Nonnull Status... statuses) {
        this.STATUS_EFFECTS.addAll(Arrays.asList(statuses));
        return this;
    }

    protected BaseMove setVolatileStatusEffect(int chance, @Nonnull BaseVolatileStatus status) {
        this.VOLATILE_STATUS_EFFECTS_CHANCE.put(status, chance);
        return this;
    }

    protected BaseMove setVolatileStatusEffect(@Nonnull BaseVolatileStatus status) {
        this.VOLATILE_STATUS_EFFECTS_CHANCE.put(status, this.EFFECT_RATE);
        return this;
    }

    protected BaseMove setRaisesOwnStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.RAISES_OWN_STATS.put(stat, amount);
        }
        return this;
    }

    protected BaseMove setLowersOwnStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.LOWERS_OWN_STATS.put(stat, amount);
        }
        return this;
    }

    protected BaseMove setRaisesOwnRandomStat(int amount) {
        this.RAISES_OWN_RANDOM_STAT = amount;
        return this;
    }

    protected BaseMove setLowersOwnRandomStat(int amount) {
        this.LOWERS_OWN_RANDOM_STAT = amount;
        return this;
    }

    protected BaseMove setRaisesEnemyStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.RAISES_ENEMY_STATS.put(stat, amount);
        }
        return this;
    }

    protected BaseMove setLowersEnemyStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.LOWERS_ENEMY_STATS.put(stat, amount);
        }
        return this;
    }

    protected BaseMove setRaisesEnemyRandomStat(int amount) {
        this.RAISES_ENEMY_RANDOM_STAT = amount;
        return this;
    }

    protected BaseMove setLowersEnemyRandomStat(int amount) {
        this.LOWERS_ENEMY_RANDOM_STAT = amount;
        return this;
    }

    protected BaseMove setRaisesTargetStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.RAISES_TARGET_STATS.put(stat, amount);
        }
        return this;
    }

    protected BaseMove setLowersTargetStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.LOWERS_TARGET_STATS.put(stat, amount);
        }
        return this;
    }

    protected BaseMove setRaisesTargetRandomStat(int amount) {
        this.RAISES_TARGET_RANDOM_STAT = amount;
        return this;
    }

    protected BaseMove setLowersTargetRandomStat(int amount) {
        this.LOWERS_TARGET_RANDOM_STAT = amount;
        return this;
    }

    protected BaseMove setCorrespondingZMove(@Nullable BaseMove correspondingZMove) {
        this.CORRESPONDING_Z_MOVE = correspondingZMove;
        return this;
    }

    protected BaseMove setZMovePower(@Nullable Integer power) {
        this.Z_MOVE_POWER = power;
        return this;
    }

    protected BaseMove setDetailedEffect(@Nullable String detailedEffect) {
        this.DETAILED_EFFECT = detailedEffect;
        return this;
    }

    protected BaseMove setPriority(int priority) {
        this.PRIORITY = priority;
        return this;
    }

    protected BaseMove setTarget(@Nullable Target target) {
        this.TARGET = target;
        return this;
    }

    protected BaseMove setPokemonHit(@Nonnull Hit hit) {
        this.POKEMON_HIT = hit;
        return this;
    }

    protected BaseMove setPhysicalContact(boolean bool) {
        this.PHYSICAL_CONTACT = bool;
        return this;
    }

    protected BaseMove setSoundType(boolean bool) {
        this.SOUND_TYPE = bool;
        return this;
    }

    protected BaseMove setPunchMove(boolean bool) {
        this.PUNCH_MOVE = bool;
        return this;
    }

    protected BaseMove setSnatchable(boolean bool) {
        this.SNATCHABLE = bool;
        return this;
    }

    protected BaseMove setZMove(boolean bool) {
        this.Z_MOVE = bool;
        return this;
    }

    protected BaseMove setDefrostsWhenUsed(boolean bool) {
        this.DEFROSTS_WHEN_USED = bool;
        return this;
    }

    protected BaseMove setHitsOppositeSideInTriples(boolean bool) {
        this.HITS_OPPOSITE_SIDE_IN_TRIPLES = bool;
        return this;
    }

    protected BaseMove setReflected(boolean bool) {
        this.REFLECTED = bool;
        return this;
    }

    protected BaseMove setBlocked(boolean bool) {
        this.BLOCKED = bool;
        return this;
    }

    protected BaseMove setCopyable(boolean bool) {
        this.COPYABLE = bool;
        return this;
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nonnull
    public Type getType() {
        return this.TYPE;
    }

    @Nonnull
    public Category getCategory() {
        return this.CATEGORY;
    }

    @Nonnull
    public MoveEffect getEffect() {
        return this.EFFECT;
    }

    public int getPP() {
        return this.PP;
    }

    public int getBasePower() {
        return this.POWER;
    }

    public int getPower(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
        return this.POWER;
    }

    public int getPowerOfZMove(BaseMove originalMove) throws IllegalZMoveException {
        int power = originalMove.getBasePower();

        switch (originalMove) {
            case MEGA_DRAIN:
                return 120;
            case WEATHER_BALL:case HEX:
                return 160;
            case GEAR_GRIND:
                return 180;
            case V_CREATE:
                return 220;
            case FLYING_PRESS:
                return 170;
            case CORE_ENFORCER:
                return 140;
        }

        if (power >= 0 && power <= 55) {
            return 100;
        } else if (power >= 60 && power <= 65) {
            return 120;
        } else if (power >= 70 && power <= 75) {
            return 140;
        } else if (power >= 80 && power <= 85) {
            return 160;
        } else if (power >= 90 && power <= 95) {
            return 175;
        } else if (power == 100) {
            return 180;
        } else if (power == 110) {
            return 185;
        } else if (power >= 120 && power <= 125) {
            return 190;
        } else if (power == 130) {
            return 195;
        } else if (power >= 140) {
            return 200;
        }

        throw new IllegalZMoveException(originalMove + " can't become Z-Move " + this.NAME);
    }

    public int getAccuracy() {
        return this.ACCURACY;
    }

    @Nonnull
    public String getBattleEffect() {
        return this.BATTLE_EFFECT;
    }

    @Nullable
    public String inDepthEffect() {
        return this.IN_DEPTH_EFFECT;
    }

    @Nonnull
    public String getSecondaryEffect() {
        return this.SECONDARY_EFFECT;
    }

    @Nullable
    public Integer getEffectRate() {
        return this.EFFECT_RATE;
    }

    public boolean isSelfZMove() {
        return this.IS_SELF_Z_MOVE;
    }

    @Nullable
    public BaseMove getCorrespondingZMove() {
        return this.CORRESPONDING_Z_MOVE;
    }

    @Nullable
    public Item getZMoveItem() {
        return this.Z_MOVE_ITEM;
    }

    @Nullable
    public String getDetailedEffect() {
        return this.DETAILED_EFFECT;
    }

    @Nullable
    public Integer getZMovePower() {
        return this.Z_MOVE_POWER;
    }

    public boolean isZMove() {
        return this.IS_Z_MOVE;
    }

    @Nullable
    public String[] getZMoveRequiredPokemon() {
        return this.Z_MOVE_REQUIRED_POKEMON.toArray(new String[]{});
    }

    @Nullable
    public BaseMove getZMoveRequiredMoves() {
        return this.Z_MOVE_REQUIRED_MOVE;
    }

    @Nullable
    public BaseMove[] getZMoveMovesThatTurnIntoThis() {
        return this.Z_MOVE_MOVES_THAT_TURN_INTO_THIS.toArray(new BaseMove[]{});
    }
    
    @Nullable
    public CriticalHitStage getCriticalHitStage() {
        return this.BASE_CRITICAL_HIT_RATE;
    }

    public int getPriority() {
        return this.PRIORITY;
    }

    @Nonnull
    public Target getTarget() {
        return this.TARGET;
    }

    @Nonnull
    public Hit getPokemonHit() {
        return this.POKEMON_HIT;
    }

    public boolean physicalContact() {
        return this.PHYSICAL_CONTACT;
    }

    public boolean soundType() {
        return this.SOUND_TYPE;
    }

    public boolean punchMove() {
        return this.PUNCH_MOVE;
    }

    public boolean snatchable() {
        return this.SNATCHABLE;
    }

    public boolean zMove() {
        return this.Z_MOVE;
    }

    public boolean defrostsWhenUsed() {
        return this.DEFROSTS_WHEN_USED;
    }

    public boolean hitsOppositeSideInTriples() {
        return this.HITS_OPPOSITE_SIDE_IN_TRIPLES;
    }

    public boolean reflected() {
        return this.REFLECTED;
    }

    public boolean blocked() {
        return this.BLOCKED;
    }

    public boolean copyable() {
        return this.COPYABLE;
    }

    public Category getCategoryOfZmove(BaseMove originalMove) {
        return originalMove.getCategory();
    }

    public CriticalHitStage getBaseCriticalHitRate() {
        return this.BASE_CRITICAL_HIT_RATE;
    }

    protected BaseMove setBaseCriticalHitRate(@Nullable CriticalHitStage baseCriticalHitRate) {
        this.BASE_CRITICAL_HIT_RATE = baseCriticalHitRate;
        return this;
    }

    protected int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, Move move) {
        if (this.CATEGORY == Category.OTHER) {
            return 0;
        }

        int attackStat;
        int defenseStat;
        int level = attacker.getLevel();
        int attackPower = this.POWER;
        double stabMultiplier = attacker.getStabMultiplier(move);
        double effectiveness = Type.getDamageMultiplier(defender.getTypes(), this.TYPE);
        double randomNumber = ThreadLocalRandom.current().nextDouble(0.85, 1.0);

        if (this.CATEGORY == Category.PHYSICAL) {
            attackStat = attacker.getStat(Stat.ATTACK);
            defenseStat = defender.getStat(Stat.DEFENSE);
        } else if (this.CATEGORY == Category.SPECIAL) {
            attackStat = attacker.getStat(Stat.SPECIAL_ATTACK);
            defenseStat = defender.getStat(Stat.SPECIAL_DEFENSE);
        } else {
            throw new InvalidCategoryException(this.CATEGORY);
        }

        return (int) (((((2 * level / 5 + 2) * attackStat * attackPower / defenseStat) / 50) + 2) * stabMultiplier * effectiveness * randomNumber);
    }

    public boolean canUseMove(@Nonnull Pokemon user) {
        return true;
    }

    public boolean canUseZMove(@Nonnull Pokemon user) {
        if (!this.Z_MOVE_REQUIRED_POKEMON.isEmpty() && !this.Z_MOVE_REQUIRED_POKEMON.contains(user.getName())) {
            return false;
        }

        return this.Z_MOVE_ITEM == user.getItem() && !Collections.disjoint(this.Z_MOVE_MOVES_THAT_TURN_INTO_THIS, user.getMoves());
    }

    protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
        return target.damage(action);
    }

    protected int use(Pokemon user, Pokemon target, Battle battle, Action action, @Nonnull Integer repeats) {
        int damage = 0;
        for (int i = 0; i < repeats; i++) {
            damage += this.use(user, target, battle, action);
        }
        return damage;
    }

    protected int use(Pokemon target, int damage) {
        target.damage(damage);
        return damage;
    }

    protected int use(Pokemon user, Battle battle) {
        // TODO: Random target choice
        return 0;
    }

    @Override
    public boolean hits(Pokemon attacker, Pokemon defender, Battle battle, Move move) {
        if (defender.isImmune(move)) {
            return false;
        }

        if (getAccuracy() == 0) {
            return true;
        }

        if (attacker.getBattle().getGeneration() == Generation.I) {
            if (Math.random() < 0.004) {
                return false;
            }
        }

        double probability = getAccuracy() * (attacker.getAccuracy() / defender.getEvasion());
        if (probability > 1 || probability < Math.random()) {
            return true;
        }

        return false;
    }

    protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
        return this.use(user, target, battle, action);
    }

    protected int miss(@Nonnull Pokemon defender) {
        PokemonDodgeEvent event = new PokemonDodgeEvent(defender);
        EventDispatcher.dispatch(event);
        return 0;
    }

    private static class Holder {
        static Map<String, BaseMove> MAP = new HashMap<>();
    }

}
