package com.github.drsmugbrain.pokemon.moves;

import com.github.drsmugbrain.pokemon.IModifier;
import com.github.drsmugbrain.pokemon.ability.Abilities;
import com.github.drsmugbrain.pokemon.battle.*;
import com.github.drsmugbrain.pokemon.events.EventDispatcher;
import com.github.drsmugbrain.pokemon.events.PokemonDodgeEvent;
import com.github.drsmugbrain.pokemon.item.Item;
import com.github.drsmugbrain.pokemon.item.ItemCategory;
import com.github.drsmugbrain.pokemon.item.Items;
import com.github.drsmugbrain.pokemon.pokemon.Gender;
import com.github.drsmugbrain.pokemon.pokemon.Pokemon;
import com.github.drsmugbrain.pokemon.pokemon.Species;
import com.github.drsmugbrain.pokemon.pokemon.Tag;
import com.github.drsmugbrain.pokemon.stats.BattleStat;
import com.github.drsmugbrain.pokemon.stats.PermanentStat;
import com.github.drsmugbrain.pokemon.status.BaseVolatileStatus;
import com.github.drsmugbrain.pokemon.status.Status;
import com.github.drsmugbrain.pokemon.status.VolatileStatus;
import com.github.drsmugbrain.pokemon.trainer.Trainer;
import com.github.drsmugbrain.pokemon.types.Type;
import com.github.drsmugbrain.util.Bot;
import com.opencsv.CSVReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 17/06/2017.
 */
public enum BaseMove implements IModifier, IMoves {

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
                target.STATS.lowerStages(1, PermanentStat.SPECIAL_DEFENSE);
            }
            return damage;
        }
    },
    ACID_ARMOR("Acid Armor") {
        @Override
        public int useAsZMove(@Nonnull Pokemon user, @Nullable Pokemon target, Battle battle, Action action) {
            user.STATS.resetLoweredStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    ACID_DOWNPOUR("Acid Downpour"),
    ACID_SPRAY("Acid Spray"),
    ACROBATICS("Acrobatics") { // Flying Gem is consumed before the power calculation is made
        @Override
        public int getPower(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
            if (target.ITEM.is()) {
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
            user.STATS.raiseStages(1, PermanentStat.SPEED);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    AGILITY("Agility") {
        @Override
        public int useAsZMove(@Nonnull Pokemon user, @Nullable Pokemon target, @Nullable Battle battle, Action action) {
            user.STATS.resetLoweredStages();
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
            user.STATS.raiseStages(2, PermanentStat.SPEED);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    AMNESIA("Amnesia") {
        @Override
        public int useAsZMove(@Nonnull Pokemon user, @Nullable Pokemon target, @Nullable Battle battle, Action action) {
            user.STATS.resetLoweredStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    ANCHOR_SHOT("Anchor Shot"),
    ANCIENT_POWER("Ancient Power"),
    AQUA_JET("Aqua Jet"),
    AQUA_RING("Aqua Ring") {
        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.STATS.raiseStages(1, PermanentStat.DEFENSE);
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
                pokemon.STATUSES.resetStatus();
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
            user.STATS.raiseStages(2, PermanentStat.SPECIAL_DEFENSE);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    ASSIST("Assist") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            List<Move> teamMoves = new ArrayList<>();
            for (Pokemon pokemon : user.getTrainer().getPokemons()) {
                teamMoves.addAll(pokemon.MOVES.get());
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
            user.ITEM.remove();

            List<Move> teamMoves = new ArrayList<>();
            for (Pokemon pokemon : user.getTrainer().getPokemons()) {
                teamMoves.addAll(pokemon.MOVES.get());
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
                BaseVolatileStatus.INFATUATION.apply(user, action);
                return super.use(user, target, battle, action);
            } else {
                return this.miss(target);
            }
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.STATS.resetLoweredStages();
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

            BaseVolatileStatus.AURORA_VEIL.apply(user, action);
            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.STATS.raiseStages(1, PermanentStat.SPEED);
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
            user.STATS.resetLoweredStages();
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
            user.STATS.resetLoweredStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    BATON_PASS("Baton Pass") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (user.getTrainer().getAliveInactivePokemons().isEmpty()) {
                return this.miss(target);
            }

            Collection<VolatileStatus> volatileStatuses = user.STATUSES.getVolatileStatuses().values();
            volatileStatuses.removeIf(status -> status.getBaseVolatileStatus() == BaseVolatileStatus.INFATUATION);

            target.STATUSES.addVolatileStatus(volatileStatuses);
            target.STATS.setStages(user);
            user.getTrainer().switchPokemon(user, target);
            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Action action) {
            user.STATS.resetLoweredStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    BEAK_BLAST("Beak Blast") {
        @Override
        public void onTurnStart(@Nonnull Action action) {
            BaseVolatileStatus.BEAK_BLAST.apply(action.getAttacker(), action);
        }

        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            BaseVolatileStatus.BEAK_BLAST.remove(user);
            return super.use(user, target, battle, action);
        }
    },
    BEAT_UP("Beat Up") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            List<Pokemon> validPokemons = user.getTrainer().getAlivePokemons();
            validPokemons.removeIf(pokemon -> pokemon.STATUSES.getStatus() != null);

            int totalDamage = 0;
            int damage;
            for (Pokemon pokemon : validPokemons) {
                double stabMultiplier = 1.0;
                if (pokemon.TYPES.getTypes().contains(this.TYPE)) {
                    stabMultiplier = pokemon.getStabMultiplier(action);
                }
                damage = (int) (((pokemon.calculate(PermanentStat.ATTACK) / 10) + 5) * stabMultiplier);
                totalDamage += damage;
                target.damage(damage);
            }

            return totalDamage;
        }
    },
    BELCH("Belch") {
        @Override
        public void onOwnItemUsed(@Nonnull Pokemon user, @Nonnull Items item) {
            if (item.getCategory() == ItemCategory.BERRY) {
                user.TAGS.put(Tag.BERRY_USED, null);
            }
        }

        @Override
        public boolean canUseMove(@Nonnull Pokemon user) {
            return user.TAGS.containsKey(Tag.BERRY_USED);
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
            if (target.ITEM.is() || !user.ITEM.is()) {
                return this.miss(target);
            }

            target.ITEM.steal(user);
            return super.use(user, target, battle, action);
        }
    },
    BIDE("Bide") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            BaseVolatileStatus.BIDE.apply(user, action);
            return super.use(user, target, battle, action);
        }
    },
    BIND("Bind") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            Action lastAction = user.getLastAction();

            if (!target.STATUSES.hasVolatileStatus(BaseVolatileStatus.BOUND)) {
                BaseVolatileStatus.BOUND.apply(user, action);
            } else if (lastAction != null && lastAction.getBaseMove() == BaseMove.BIND &&
                       lastAction.getTarget() == target) {
                action.increasePP(1);
            }

            return super.use(user, target, battle, action);
        }

        @Override
        public int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action, @Nullable DamageTags... tags) {
            int damage;
            switch (attacker.getBattle().getGeneration()) {
                case I:
                    if (defender.TYPES.isImmune(action)) {
                        return 0;
                    }

                    return super.getDamage(attacker, defender, action);
                case II:
                case III:
                case IV:
                case V:
                    damage = (int) (defender.calculate(PermanentStat.HP) / 16);
                    break;
                case VI:
                case VII:
                    damage = (int) (defender.calculate(PermanentStat.HP) / 8);
                    break;
                default:
                    throw new InvalidGenerationException(attacker.getBattle().getGeneration());
            }

            action.setDamage(defender, damage);
            return damage;
        }

        @Override
        public boolean hits(@Nonnull Pokemon target, @Nonnull Action action) {
            Generation generation = action.getAttacker().getBattle().getGeneration();
            if (generation == Generation.I) {
                return super.hitsIgnoreTypes(action);
            }

            return super.hits(target, action);
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
            user.STATS.raiseStages(1, PermanentStat.DEFENSE);
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
            if (!user.STATUSES.hasVolatileStatus(BaseVolatileStatus.BOUNCE)) {
                VolatileStatus status = new VolatileStatus(BaseVolatileStatus.BOUNCE, action, 2);
                user.STATUSES.addVolatileStatus(status);
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
                        pokemon.STATUSES.removeVolatileStatus(BaseVolatileStatus.LIGHT_SCREEN, BaseVolatileStatus.REFLECT);
                    }
                    break;
                case V:
                case VI:
                case VII:
                    if (target.TYPES.isImmune(action)) {
                        break;
                    }

                    for (Pokemon pokemon : target.getTrainer().getActivePokemons()) {
                        pokemon.STATUSES.removeVolatileStatus(BaseVolatileStatus.LIGHT_SCREEN, BaseVolatileStatus.REFLECT, BaseVolatileStatus.AURORA_VEIL);
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
            int defenderCurrentHP = target.getHP();
            int defenderMaxHP = target.getHP();
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
            Item item = target.ITEM;
            if (item.is(ItemCategory.BERRY)) {
                item.use(user);
            }

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
                    if (user.STATUSES.getStatus() == Status.FREEZE) {
                        Status.FREEZE.remove(user);
                    }
                    break;
            }

            List<Type> types = user.TYPES.getTypes();
            if (types.contains(Type.FIRE)) {
                types.remove(Type.FIRE);
                if (types.size() == 0) {
                    types.add(Type.TYPELESS);
                }

                user.TYPES.setTypes(types);
            }

            return super.use(user, target, battle, action);
        }

        @Override
        public boolean hits(@Nonnull Pokemon target, @Nonnull Action action) {
            if (!action.getAttacker().TYPES.isType(Type.FIRE)) {
                return false;
            }

            return super.hits(target, action);
        }
    },
    CALM_MIND("Calm Mind"),
    CAMOUFLAGE("Camouflage") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.TYPES.setTypes(Type.NORMAL);
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
            user.STATS.raiseStages(2, PermanentStat.SPECIAL_DEFENSE);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    CATASTROPIKA("Catastropika"),
    CELEBRATE("Celebrate") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.raiseStages(1, PermanentStat.ATTACK);
            user.STATS.raiseStages(1, PermanentStat.DEFENSE);
            user.STATS.raiseStages(1, PermanentStat.SPECIAL_ATTACK);
            user.STATS.raiseStages(1, PermanentStat.SPECIAL_DEFENSE);
            user.STATS.raiseStages(1, PermanentStat.SPEED);

            return super.useAsZMove(user, target, battle, action);
        }
    },
    CHARGE("Charge") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            BaseVolatileStatus.CHARGE.apply(user, action);

            switch (battle.getGeneration()) {
                case I:
                case II:
                case III:
                    break;
                case IV:
                case V:
                case VI:
                case VII:
                    user.STATS.raiseStages(1, PermanentStat.SPECIAL_DEFENSE);
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.raiseStages(1, PermanentStat.SPECIAL_DEFENSE);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    CHARGE_BEAM("Charge Beam"),
    CHARM("Charm"),
    CHATTER("Chatter"),
    CHIP_AWAY("Chip Away") {
        @Override
        public int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action, @Nullable DamageTags... tags) {
            return super.getDamage(attacker, defender, action, DamageTags.NO_STAGES);
        }

        @Override
        public boolean hits(@Nonnull Pokemon target, @Nonnull Action action) {
            return super.hitsWithoutDefenderStages(action);
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

            if (!target.STATUSES.hasVolatileStatus(BaseVolatileStatus.BOUND)) {
                BaseVolatileStatus.BOUND.apply(user, action);
            } else if (lastAction.getBaseMove() == BaseMove.CLAMP &&
                       lastAction.getTarget() == target) {
                action.increasePP(1);
            }

            return super.use(user, target, battle, action);
        }

        @Override
        public int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action, @Nullable DamageTags... tags) {
            int damage;
            switch (attacker.getBattle().getGeneration()) {
                case I:
                    if (defender.TYPES.isImmune(action)) {
                        return 0;
                    }

                    return super.getDamage(attacker, defender, action);
                case II:
                case III:
                case IV:
                case V:
                    damage = (int) (defender.calculate(PermanentStat.HP) / 16);
                    break;
                case VI:
                case VII:
                    damage = (int) (defender.calculate(PermanentStat.HP) / 8);
                    break;
                default:
                    throw new InvalidGenerationException(attacker.getBattle().getGeneration());
            }

            action.setDamage(defender, damage);
            return damage;
        }

        @Override
        public boolean hits(@Nonnull Pokemon target, @Nonnull Action action) {
            Generation generation = action.getAttacker().getBattle().getGeneration();
            if (generation == Generation.I) {
                return super.hitsIgnoreTypes(action);
            }

            return super.hits(target, action);
        }
    },
    CLANGING_SCALES("Clanging Scales"),
    CLEAR_SMOG("Clear Smog") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            target.STATS.resetStages();
            return super.use(user, target, battle, action);
        }
    },
    CLOSE_COMBAT("Close Combat"),
    COIL("Coil") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.resetLoweredStages();
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
                    if (!target.STATUSES.hasVolatileStatus(BaseVolatileStatus.SUBSTITUTE)) {
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

                        if (!target.STATUSES.hasVolatileStatus(BaseVolatileStatus.SUBSTITUTE)) {
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
            user.STATS.raiseStages(1, PermanentStat.SPECIAL_DEFENSE);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    CONFUSE_RAY("Confuse Ray") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.raiseStages(1, PermanentStat.SPECIAL_ATTACK);
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
                    user.TYPES.setTypes(target.TYPES.getTypes());
                    break;
                case II:
                case III:
                case IV: {
                    List<Type> moveTypes = user.MOVES.get().stream().map(Move::getType).collect(Collectors.toList());
                    moveTypes.remove(Type.CURSE);

                    int randomIndex = ThreadLocalRandom.current().nextInt(moveTypes.size());
                    Type randomType = moveTypes.get(randomIndex);

                    user.TYPES.setTypes(randomType);
                    break;
                }
                case V: {
                    List<Type> moveTypes = user.MOVES.get().stream().map(Move::getType).collect(Collectors.toList());

                    int randomIndex = ThreadLocalRandom.current().nextInt(moveTypes.size());
                    Type randomType = moveTypes.get(randomIndex);

                    user.TYPES.setTypes(randomType);
                    break;
                }
                case VI:
                case VII:
                    Type firstMoveType = user.MOVES.get().get(0).getType();
                    user.TYPES.setTypes(firstMoveType);
                    break;
            }

            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.raiseStages(1, PermanentStat.ATTACK);
            user.STATS.raiseStages(1, PermanentStat.DEFENSE);
            user.STATS.raiseStages(1, PermanentStat.SPECIAL_ATTACK);
            user.STATS.raiseStages(1, PermanentStat.SPECIAL_DEFENSE);
            user.STATS.raiseStages(1, PermanentStat.SPEED);

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

                    typesResisted.removeAll(user.TYPES.getTypes());

                    if (typesResisted.isEmpty()) {
                        return this.miss(target);
                    }
                    break;
                default:
                    throw new InvalidGenerationException(battle.getGeneration());
            }

            int randomIndex = ThreadLocalRandom.current().nextInt(typesResisted.size());
            Type randomType = typesResisted.get(randomIndex);

            user.TYPES.setTypes(randomType);

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

            return copiedMove.tryUse(user, target, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.raiseStages(1, BattleStat.ACCURACY);

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

            user.ITEM.remove();
            if (copiedMove.getCategory() != Category.OTHER) {
                return copiedMove.replaceAsZMove(action, user, target);
            } else {
                return copiedMove.tryUse(action);
            }
        }
    },
    CORE_ENFORCER("Core Enforcer") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (target.movedThisTurn()) {
                switch (target.ABILITY.get()) {
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
                        target.ABILITY.setSuppressed(true);
                }
            }

            return super.use(user, target, battle, action);
        }
    },
    CORKSCREW_CRASH("Corkscrew Crash"),
    COSMIC_POWER("Cosmic Power") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.raiseStages(1, PermanentStat.SPECIAL_DEFENSE);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    COTTON_GUARD("Cotton Guard") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.resetLoweredStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    COTTON_SPORE("Cotton Spore") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.resetLoweredStages();
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

            Integer actionDamage = lastAction.getDamage(user);
            if (actionDamage == null) {
                return this.miss(user);
            }

            switch (battle.getGeneration()) {
                case I:
                    if (actionDamage > 0
                        && (lastAction.getType() == Type.NORMAL || lastAction.getType() == Type.FIGHTING)
                        && lastAction.getBaseMove() != COUNTER) {
                        int damage;
                        if (lastAction.getTargetVolatileStatuses().contains(BaseVolatileStatus.SUBSTITUTE)) {
                            damage = lastAction.getBaseMove().getDamage(lastAction.getAttacker(), lastAction.getTarget(), lastAction);
                        } else {
                            damage = actionDamage * 2;
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
                    if (lastAction.getTargetVolatileStatuses().contains(BaseVolatileStatus.SUBSTITUTE)) {
                        return this.miss(user);
                    }

                    if (lastAction.getCategory() == Category.PHYSICAL) {
                        int damage = actionDamage * 2;
                        target.damage(damage);
                        return damage;
                    }
                    break;
                case VII:
                    if (lastAction.getTargetVolatileStatuses().contains(BaseVolatileStatus.SUBSTITUTE)) {
                        return this.miss(user);
                    }

                    if (lastAction.getCategory() == Category.PHYSICAL) {
                        int damage = actionDamage * 2;
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
        public boolean hits(@Nonnull Pokemon target, @Nonnull Action action) {
            Generation generation = action.getAttacker().getBattle().getGeneration();
            if (generation == Generation.I) {
                return super.hitsIgnoreTypes(action);
            }

            return super.hits(target, action);
        }
    },
    COVET("Covet") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage = super.use(user, target, battle, action);

            Item userItem = user.ITEM;
            Items targetItem = target.ITEM.get();
            ItemCategory targetItemCategory = targetItem != null ? targetItem.getCategory() : null;
            if (!userItem.is() && target.ITEM.is() && !target.STATUSES.hasVolatileStatus(BaseVolatileStatus.SUBSTITUTE)) {
                switch (battle.getGeneration()) {
                    case I:
                    case II:
                    case III:
                        userItem.steal(target);
                        break;
                    case IV:
                        if (target.ABILITY.get() == Abilities.MULTITYPE || targetItem == Items.GRISEOUS_ORB) {
                            break;
                        }

                        userItem.steal(target);
                        break;
                    case V: {
                        if (user.isFainted()) {
                            break;
                        }

                        Species userPokemon = user.getSpecies();
                        Species targetPokemon = target.getSpecies();
                        if (targetItem == Items.GRISEOUS_ORB) {
                            if (userPokemon == Species.GIRATINA || targetPokemon == Species.GIRATINA) {
                                break;
                            }
                        }

                        if (targetItemCategory == ItemCategory.ARCEUS_PLATE) {
                            if (Species.isArceus(user) || Species.isArceus(target)) {
                                break;
                            }
                        }

                        if (targetItemCategory == ItemCategory.GENESECT_DRIVE) {
                            if (user.getSpecies() == Species.GENESECT || target.getSpecies() == Species.GENESECT) {
                                break;
                            }
                        }

                        userItem.steal(target);
                        break;
                    }
                    case VI:
                    case VII: {
                        if (user.isFainted()) {
                            break;
                        }

                        Species userPokemon = user.getSpecies();
                        Species targetPokemon = target.getSpecies();
                        if (targetItem == Items.GRISEOUS_ORB) {
                            if (userPokemon == Species.GIRATINA || targetPokemon == Species.GIRATINA) {
                                break;
                            }
                        }

                        if (targetItemCategory == ItemCategory.ARCEUS_PLATE) {
                            if (Species.isArceus(user) || Species.isArceus(target)) {
                                break;
                            }
                        }

                        if (targetItemCategory == ItemCategory.GENESECT_DRIVE) {
                            if (user.getSpecies() == Species.GENESECT || target.getSpecies() == Species.GENESECT) {
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
                            if (Species.isSilvally(user) || Species.isSilvally(target)) {
                                break;
                            }
                        }

                        userItem.steal(target);
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
                BaseVolatileStatus.CRAFTY_SHIELD.apply(user, action);
            }

            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.raiseStages(1, PermanentStat.SPECIAL_DEFENSE);
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
                        target.STATS.lowerStages(1, PermanentStat.SPECIAL_DEFENSE);
                    }
                    break;
                case IV:
                case V:
                case VI:
                case VII:
                    if (chance < 0.2) {
                        target.STATS.lowerStages(1, PermanentStat.DEFENSE);
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
            int targetCurrentHP = target.getHP();
            int targetMaxHP = target.getMaxHP();

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

            if (user.TYPES.isType(Type.GHOST)) {
                user.damage(50.0);
                BaseVolatileStatus.CURSE.apply(user, action);
            } else {
                user.STATS.lowerStages(1, PermanentStat.SPEED);
                user.STATS.raiseStages(1, PermanentStat.ATTACK);
                user.STATS.raiseStages(1, PermanentStat.DEFENSE);
            }

            return damage;
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (user.TYPES.isType(Type.GHOST)) {
                user.heal(100.0);
            } else {
                user.STATS.raiseStages(1, PermanentStat.ATTACK);
            }

            return super.useAsZMove(user, target, battle, action);
        }

        @Override
        public boolean hits(@Nonnull Pokemon target, @Nonnull Action action) {
            Pokemon attacker = action.getAttacker();
            Pokemon defender = action.getTarget();

            if (action.targetHasVolatileStatus(BaseVolatileStatus.CURSE)) {
                return false;
            }

            if (!defender.getTrainer().hasOpponentOnField()) {
                return false;
            }

            Generation generation = attacker.getBattle().getGeneration();
            switch (generation) {
                case I:
                case II:
                    if (defender.STATUSES.hasVolatileStatus(BaseVolatileStatus.SUBSTITUTE)) {
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
                    if (attacker.TYPES.isType(Type.GHOST) && attacker == defender) {
                        attacker.retarget(attacker.getEnemyOppositePokemon());
                    }
                    break;
                case VI:
                case VII:
                    if (attacker.TYPES.isType(Type.GHOST) && attacker == defender) {

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

            return super.hits(target, action);
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
                            adjacentEnemyPokemon.STATUSES.setStatus(Status.SLEEP);
                        }
                    }
                    break;
                case VII:
                    for (Pokemon adjacentEnemyPokemon : adjacentEnemyPokemons) {
                        if (Math.random() < 0.5) {
                            adjacentEnemyPokemon.STATUSES.setStatus(Status.SLEEP);
                        }
                    }
                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }

            return damage;
        }

        @Override
        public boolean hits(@Nonnull Pokemon target, @Nonnull Action action) {
            Pokemon attacker = action.getAttacker();
            Generation generation = attacker.getBattle().getGeneration();
            if (generation == Generation.VII) {
                if (attacker.getSpecies() != Species.DARKRAI) {
                    return false;
                }
            }

            return super.hits(target, action);
        }
    },
    DARKEST_LARIAT("Darkest Lariat") {
        @Override
        public int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action, @Nullable DamageTags... tags) {
            return super.getDamage(attacker, defender, action, DamageTags.NO_STAGES);
        }

        @Override
        public boolean hits(@Nonnull Pokemon target, @Nonnull Action action) {
            return super.hitsWithoutDefenderStages(action);
        }
    },
    DAZZLING_GLEAM("Dazzling Gleam"),
    DEFEND_ORDER("Defend Order") {
        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.raiseStages(1, PermanentStat.DEFENSE);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    DEFENSE_CURL("Defense Curl") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            BaseVolatileStatus.DEFENSE_CURL.apply(user, action);
            return super.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.raiseStages(1, BattleStat.ACCURACY);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    DEFOG("Defog") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage = super.use(user, target, battle, action);

            Trainer enemyTrainer = target.getTrainer();
            Generation generation = user.getBattle().getGeneration();
            switch (generation) {
                case I:
                case II:
                case III:
                case IV:
                    target.STATS.lowerStages(1, BattleStat.EVASION);

                    battle.setWeather(Weather.FOG);

                    enemyTrainer.removeVolatileStatus(
                            BaseVolatileStatus.LIGHT_SCREEN,
                            BaseVolatileStatus.REFLECT,
                            BaseVolatileStatus.SAFEGUARD,
                            BaseVolatileStatus.MIST
                    );

                    enemyTrainer.removeEntryHazard(
                            EntryHazard.SPIKES,
                            EntryHazard.TOXIC_SPIKES,
                            EntryHazard.STEALTH_ROCK
                    );
                    break;
                case V:
                    if (!action.targetHasVolatileStatus(BaseVolatileStatus.SUBSTITUTE)) {
                        target.STATS.lowerStages(1, BattleStat.EVASION);
                    }

                    battle.setWeather(Weather.FOG);

                    enemyTrainer.removeVolatileStatus(
                            BaseVolatileStatus.LIGHT_SCREEN,
                            BaseVolatileStatus.REFLECT,
                            BaseVolatileStatus.SAFEGUARD,
                            BaseVolatileStatus.MIST
                    );

                    enemyTrainer.removeEntryHazard(
                            EntryHazard.SPIKES,
                            EntryHazard.TOXIC_SPIKES,
                            EntryHazard.STEALTH_ROCK
                    );
                    break;
                case VI:
                    if (!action.targetHasVolatileStatus(BaseVolatileStatus.SUBSTITUTE)) {
                        target.STATS.lowerStages(1, BattleStat.EVASION);
                    }

                    battle.setWeather(Weather.FOG);

                    enemyTrainer.removeVolatileStatus(
                            BaseVolatileStatus.LIGHT_SCREEN,
                            BaseVolatileStatus.REFLECT,
                            BaseVolatileStatus.SAFEGUARD,
                            BaseVolatileStatus.MIST
                    );

                    enemyTrainer.removeEntryHazard(
                            EntryHazard.SPIKES,
                            EntryHazard.TOXIC_SPIKES,
                            EntryHazard.STEALTH_ROCK,
                            EntryHazard.STICKY_WEB
                    );
                    user.getTrainer().removeEntryHazard(
                            EntryHazard.SPIKES,
                            EntryHazard.TOXIC_SPIKES,
                            EntryHazard.STEALTH_ROCK,
                            EntryHazard.STICKY_WEB
                    );
                    break;
                case VII:
                    if (!action.targetHasVolatileStatus(BaseVolatileStatus.SUBSTITUTE)) {
                        target.STATS.lowerStages(1, BattleStat.EVASION);
                    }

                    battle.setWeather(Weather.FOG);

                    enemyTrainer.removeVolatileStatus(
                            BaseVolatileStatus.LIGHT_SCREEN,
                            BaseVolatileStatus.REFLECT,
                            BaseVolatileStatus.SAFEGUARD,
                            BaseVolatileStatus.MIST,
                            BaseVolatileStatus.AURORA_VEIL
                    );

                    enemyTrainer.removeEntryHazard(
                            EntryHazard.SPIKES,
                            EntryHazard.TOXIC_SPIKES,
                            EntryHazard.STEALTH_ROCK,
                            EntryHazard.STICKY_WEB
                    );
                    user.getTrainer().removeEntryHazard(
                            EntryHazard.SPIKES,
                            EntryHazard.TOXIC_SPIKES,
                            EntryHazard.STEALTH_ROCK,
                            EntryHazard.STICKY_WEB
                    );
                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }

            return damage;
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.raiseStages(1, BattleStat.ACCURACY);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    DESTINY_BOND("Destiny Bond") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage = super.use(user, target, battle, action);

            user.TAGS.put(Tag.DESTINY_BOND, action);

            return damage;
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            target.retarget(user);
            return super.useAsZMove(user, target, battle, action);
        }

        @Override
        public boolean hits(@Nonnull Pokemon target, @Nonnull Action action) {
            Pokemon attacker = action.getAttacker();
            Generation generation = attacker.getBattle().getGeneration();
            switch (generation) {
                case I:
                case II:
                case III:
                case IV:
                case V:
                case VI:
                    return super.hits(target, action);
                case VII:
                    Action lastAction = attacker.getLastAction();
                    if (lastAction == null) {
                        break;
                    }

                    Boolean hit = lastAction.hit(attacker);
                    if(hit == null) {
                        break;
                    }

                    if (lastAction.getBaseMove() == DESTINY_BOND && hit) {
                        return false;
                    }

                    break;
                default:
                    throw new InvalidGenerationException(generation);
            }

            return super.hits(target, action);
        }
    },
    DETECT("Detect") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            return PROTECT.use(user, target, battle, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.raiseStages(1, BattleStat.EVASION);
            return super.useAsZMove(user, target, battle, action);
        }
    },
    DEVASTATING_DRAKE("Devastating Drake"),
    DIAMOND_STORM("Diamond Storm") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int damage = super.use(user, target, battle, action);

            double random = ThreadLocalRandom.current().nextDouble();
            switch (action.getGeneration()) {
                case I:
                case II:
                case III:
                case IV:
                case V:
                case VI:
                    if (random < 0.5) {
                        user.STATS.raiseStages(1, PermanentStat.DEFENSE);
                    }
                    break;
                case VII:
                    if (random < 0.5) {
                        user.STATS.raiseStages(2, PermanentStat.DEFENSE);
                    }
                    break;
                default:
                    throw new InvalidGenerationException(action.getGeneration());
            }

            return damage;
        }
    },
    DIG("Dig") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (!action.attackerHasVolatileStatus(BaseVolatileStatus.SEMI_INVULNERABLE)) {
                BaseVolatileStatus.SEMI_INVULNERABLE.apply(action.getAttacker(), action);
                return 0;
            } else {
                BaseVolatileStatus.SEMI_INVULNERABLE.remove(action.getAttacker());
                return super.use(user, target, battle, action);
            }
        }
    },
    DISABLE("Disable") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            int duration;
            switch (action.getGeneration()) {
                case I:
                    List<Move> targetMoves = new ArrayList<>(target.MOVES.get());
                    targetMoves.removeIf(move -> move.getPP() <= 0);

                    int randomIndex = ThreadLocalRandom.current().nextInt(targetMoves.size());
                    Move randomMove = targetMoves.get(randomIndex);
                    duration = ThreadLocalRandom.current().nextInt(6 + 1);

                    target.MOVES.disable(duration, randomMove);

                    return super.use(user, target, battle, action);
                case II:
                    duration = ThreadLocalRandom.current().nextInt(2, 8 + 1);
                    break;
                case III:
                    duration = ThreadLocalRandom.current().nextInt(2, 5 + 1);
                    break;
                case IV:
                    duration = ThreadLocalRandom.current().nextInt(4, 7 + 1);
                    break;
                case V:
                case VI:
                case VII:
                    duration = 4;
                    break;
                default:
                    throw new InvalidGenerationException(action.getGeneration());
            }

            Action lastAction = target.getLastAction();
            target.MOVES.disable(duration, lastAction);

            return super.use(user, target, battle, action);
        }

        @Override
        public boolean hits(@Nonnull Pokemon target, @Nonnull Action action) {
            if (target.MOVES.hasDisabled() || !target.MOVES.hasPP()) {
                return false;
            }

            switch (action.getGeneration()) {
                case I:
                    break;
                case II:
                case III:
                case IV:
                case V:
                case VI:
                case VII:
                    Action lastAction = target.getLastAction();
                    if (lastAction == null || lastAction.getBaseMove() == STRUGGLE) {
                        return false;
                    }
                    break;
                default:
                    throw new InvalidGenerationException(action.getGeneration());
            }

            return super.hits(target, action);
        }

        @Override
        protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
            user.STATS.resetLoweredStages();
            return super.useAsZMove(user, target, battle, action);
        }
    },
    DISARMING_VOICE("Disarming Voice"),
    DISCHARGE("Discharge"),
    DIVE("Dive") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (!action.attackerHasVolatileStatus(BaseVolatileStatus.SEMI_INVULNERABLE)) {
                BaseVolatileStatus.SEMI_INVULNERABLE.apply(action.getAttacker(), action);
                return 0;
            } else {
                BaseVolatileStatus.SEMI_INVULNERABLE.remove(action.getAttacker());
                return super.use(user, target, battle, action);
            }
        }
    },
    DIZZY_PUNCH("Dizzy Punch") {
        @Override
        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
            if (action.getGeneration() == Generation.I) {
                return super.use(user, target, battle, action);
            } else {
                int damage = super.use(user, target, battle, action);
                if (Math.random() < this.EFFECT_RATE) {
                    VolatileStatus status = new VolatileStatus(BaseVolatileStatus.CONFUSION, action);
                    target.STATUSES.addVolatileStatus(status);
                }
                return damage;
            }
        }
    },
    DOOM_DESIRE("Doom Desire") {
//        @Override
//        protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
//            switch (action.getGeneration()) {
//                case I:
//                case II:
//                case III:
//                    if (!user.TAGS.containsKey(Tag.DOOM_DESIRE)) {
//                        Tag.DOOM_DESIRE.apply(user, action);
//                        message(user, target, action);
//                        return 0;
//                    } else {
//                        Pokemon tagTarget = user.TAGS.get(Tag.DOOM_DESIRE).getTarget();
//                        int damage = getDamage(user, tagTarget, action);
//                        target.damage(damage);
//                    }
//                    break;
//                case IV:
//                    break;
//                case V:
//                    break;
//                case VI:
//                    break;
//                case VII:
//                    break;
//                default:
//                    throw new InvalidGenerationException(action.getGeneration());
//            }
//        }

        @Override
        public int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action, @Nullable DamageTags... tags) {
            return super.getDamage(attacker, defender, action, DamageTags.NO_CRITICAL, DamageTags.NO_EFFECTIVENESS, DamageTags.NO_STAB);
        }
    },
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
                    if (user.STATUSES.getStatus() == Status.FREEZE) {
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
                    if (user.STATUSES.getStatus() == Status.FREEZE) {
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
                    if (user.STATUSES.getStatus() == Status.FREEZE) {
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
                            pokemon.STATS.resetStages();
                            pokemon.removeStatModifier(Status.BURN, Status.PARALYSIS);
//                            pokemon.removeVolatileStatus(); // TODO: Removes effects of focus energy, dire hit, mist, guard spec, x accuracy, leech sed, disable, reflect, light screen
                            pokemon.STATUSES.removeVolatileStatus(BaseVolatileStatus.CONFUSION);
                            if (pokemon.STATUSES.getStatus() == Status.BADLY_POISONED) {
                                pokemon.STATUSES.setStatus(Status.POISON);
                            }
                            if (pokemon != user) {
                                pokemon.STATUSES.resetStatus();
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
                            if (battle.getGeneration() == Generation.III && pokemon.STATUSES.hasVolatileStatus(BaseVolatileStatus.PROTECTION)) {
                                this.miss(user);
                            }
                            pokemon.STATS.resetStages();
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
                    if (user.STATUSES.getStatus() == Status.FREEZE) {
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
                    if (user.STATUSES.getStatus() == Status.FREEZE) {
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
                    if (user.STATUSES.getStatus() == Status.FREEZE) {
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
                if (target.STATUSES.getStatus() == Status.FREEZE && Math.random() < 0.3) {
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

    private static List<String[]> csv;

    @Nonnull
    public final String NAME;

    public final int POWER;

    @Nullable
    public final Integer EFFECT_RATE;

    @Nonnull
    public final Type TYPE;

    @Nonnull
    public final Category CATEGORY;

    public final MoveEffect EFFECT = null;

    public final int PP;

    public final int ACCURACY;

    public final boolean IS_Z_MOVE;

    @Nonnull
    public final List<Species> Z_MOVE_REQUIRED_POKEMON;

    @Nonnull
    public final BaseMoves.Single Z_MOVE_REQUIRED_MOVES;

    @Nonnull
    public final BaseMoves Z_PRECURSOR_MOVES;

    public final boolean IS_SELF_Z_MOVE;

    @Nullable
    public final Items Z_MOVE_ITEM;

    @Nonnull
    public final String BATTLE_EFFECT;

    @Nullable
    public final String IN_DEPTH_EFFECT;

    @Nonnull
    public final String SECONDARY_EFFECT;

    @Nonnull
    public final BaseMoves.Single CORRESPONDING_Z_MOVE;

    @Nullable
    public final Integer Z_MOVE_POWER;

    @Nullable
    public final String DETAILED_EFFECT;

    @Nullable
    public final CriticalHitStage BASE_CRITICAL_HIT_RATE;

    public final int PRIORITY;

    @Nonnull
    public final Target TARGET;

    @Nonnull
    public final Hit POKEMON_HIT;

    public final boolean PHYSICAL_CONTACT;

    public final boolean SOUND_TYPE;

    public final boolean PUNCH_MOVE;

    public final boolean SNATCHABLE;

    public final boolean Z_MOVE;

    public final boolean DEFROSTS_WHEN_USED;

    public final boolean HITS_OPPOSITE_SIDE_IN_TRIPLES;

    public final boolean REFLECTED;

    public final boolean BLOCKED;

    public final boolean COPYABLE;

    BaseMove(@Nonnull String name) {
        Holder.MAP.put(name.toLowerCase(), this);

        String[] line = getLine(name);
        if (line == null) {
            Bot.LOGGER.error("Couldn't find csv line for move " + name);
            System.exit(1);
        }

        NAME = name;
        TYPE = Type.getType(line[1]);
        CATEGORY = Category.getCategory(line[2]);
        PP = Integer.parseInt(line[3]);
        POWER = Integer.parseInt(line[4]);
        ACCURACY = Integer.parseInt(line[5]);
        BATTLE_EFFECT = line[6];
        IN_DEPTH_EFFECT = line[7].isEmpty() ? null : line[7];
        SECONDARY_EFFECT = line[8];
        EFFECT_RATE = line[9].isEmpty() ? null : Integer.valueOf(line[9]);
        IS_SELF_Z_MOVE = Boolean.parseBoolean(line[10]);
        CORRESPONDING_Z_MOVE = new BaseMoves.Single(line[11]);
        Z_MOVE_ITEM = line[12].isEmpty() ? null : Items.getItem(line[12]);
        DETAILED_EFFECT = line[13].isEmpty() ? null : line[13];
        Z_MOVE_POWER = line[14].isEmpty() ? null : Integer.valueOf(line[14]);
        IS_Z_MOVE = Boolean.parseBoolean(line[15]);

        List<Species> species = new ArrayList<>();
        if (!line[16].isEmpty()) {
            for (String s : line[16].split(",")) {
                species.add(Species.getPokemon(s));
            }
        }
        Z_MOVE_REQUIRED_POKEMON = Collections.unmodifiableList(species);

        Z_MOVE_REQUIRED_MOVES = new BaseMoves.Single(line[17]);
        Z_PRECURSOR_MOVES = new BaseMoves(line[18].split(","));
        BASE_CRITICAL_HIT_RATE = line[19].isEmpty() ? null : CriticalHitStage.getStage(Double.parseDouble(line[19]));
        PRIORITY = Integer.parseInt(line[20]);
        TARGET = Target.getTarget(line[21]);
        POKEMON_HIT = Hit.getHit(line[22]);
        PHYSICAL_CONTACT = Boolean.parseBoolean(line[23]);
        SOUND_TYPE = Boolean.parseBoolean(line[24]);
        PUNCH_MOVE = Boolean.parseBoolean(line[25]);
        SNATCHABLE = Boolean.parseBoolean(line[26]);
        Z_MOVE = Boolean.parseBoolean(line[27]);
        DEFROSTS_WHEN_USED = Boolean.parseBoolean(line[28]);
        HITS_OPPOSITE_SIDE_IN_TRIPLES = Boolean.parseBoolean(line[29]);
        REFLECTED = Boolean.parseBoolean(line[30]);
        BLOCKED = Boolean.parseBoolean(line[31]);
        COPYABLE = Boolean.parseBoolean(line[32]);
    }

    @Nonnull
    private static List<String[]> readCsv() {
        try {
            FileReader fileReader = new FileReader("moves.csv");
            CSVReader reader = new CSVReader(fileReader, ',');
            return reader.readAll();
        } catch (FileNotFoundException e) {
            Bot.LOGGER.error("Moves.csv file not found", e);
        } catch (IOException e) {
            Bot.LOGGER.error("Error reading csv lines", e);
        }

        System.exit(1);
        return null;
    }

    @Nullable
    private static String[] getLine(@Nonnull String move) {
        if (csv == null) {
            csv = readCsv();
        }

        for (String[] line : csv) {
            if (Objects.equals(line[0], move)) {
                return line;
            }
        }

        return null;
    }

    public static BaseMove getMove(@Nonnull String move) {
        move = move.toLowerCase();
        if (move.startsWith("hidden power")) {
            return Holder.MAP.get("hidden power");
        }
        if (!Holder.MAP.containsKey(move)) {
            for (BaseMove baseMove : BaseMove.values()) {
                if (Objects.equals(baseMove.NAME.toLowerCase(), move)) {
                    return baseMove;
                }
            }
            throw new NullPointerException("Move " + move + " doesn't exist");
        }

        return Holder.MAP.get(move);
    }

    public int getPower(Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
        return POWER;
    }

    public int getPowerOfZMove(BaseMove originalMove) throws IllegalZMoveException {
        int power = originalMove.POWER;

        switch (originalMove) {
            case MEGA_DRAIN:
                return 120;
            case WEATHER_BALL:
            case HEX:
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

        throw new IllegalZMoveException(originalMove + " can't become Z-Move " + NAME);
    }

    protected boolean isCritical(@Nonnull Action action) {
        return true;
    }

    public int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender, @Nonnull Action action, @Nullable DamageTags... tags) {
        List<DamageTags> damageTags = new ArrayList<>();
        if (tags != null) {
            damageTags = Arrays.asList(tags);
        }
        if (CATEGORY == Category.OTHER) {
            return 0;
        }

        double attackStat;
        double defenseStat;
        int level = attacker.getLevel();
        int attackPower = POWER;

        double targets = 1.0;
        if (POKEMON_HIT.hitsMultiple()) {
            targets = 0.75;
        }

        double weatherMultiplier = action.getBattle().getWeather().damageMultiplier(action);

        double criticalMultiplier = 1.0;
        if (!damageTags.contains(DamageTags.NO_CRITICAL) && isCritical(action)) {
            action.setCritical(defender, true);
            switch (action.getGeneration()) {
                case I:
                    level *= 2;
                    break;
                case II:
                case III:
                case IV:
                case V:
                    criticalMultiplier = 2.0;
                    break;
                case VI:
                case VII:
                    criticalMultiplier = 1.5;
                    break;
                default:
                    throw new InvalidGenerationException(action.getGeneration());
            }
        }

        double stabMultiplier = 1.0;
        if (!damageTags.contains(DamageTags.NO_STAB)) {
            stabMultiplier = attacker.getStabMultiplier(action);
        }

        double effectiveness = 1.0;
        if (!damageTags.contains(DamageTags.NO_EFFECTIVENESS)) {
            effectiveness = defender.TYPES.damageMultiplier(action);
        }
        double randomNumber = ThreadLocalRandom.current().nextDouble(0.85, 1.0);

        if (CATEGORY == Category.PHYSICAL) {
            attackStat = attacker.calculate(PermanentStat.ATTACK);
            if (damageTags.contains(DamageTags.NO_STAGES)) {
                defenseStat = defender.calculateWithoutStages(PermanentStat.DEFENSE);
            } else {
                defenseStat = defender.calculate(PermanentStat.DEFENSE);
            }
        } else if (CATEGORY == Category.SPECIAL) {
            attackStat = attacker.calculate(PermanentStat.SPECIAL_ATTACK);
            if (damageTags.contains(DamageTags.NO_STAGES)) {
                defenseStat = defender.calculateWithoutStages(PermanentStat.SPECIAL_DEFENSE);
            } else {
                defenseStat = defender.calculate(PermanentStat.SPECIAL_DEFENSE);
            }
        } else {
            throw new InvalidCategoryException(CATEGORY);
        }

        int damage = (int) (
                (((((2 * level) / 5 + 2) * attackPower * attackStat / defenseStat) / 50) + 2)
                * targets * weatherMultiplier * criticalMultiplier * randomNumber * stabMultiplier * effectiveness
        );
        action.setDamage(defender, damage);

        return damage;
    }

    public boolean canUseMove(@Nonnull Pokemon user) {
        return true;
    }

    public boolean canUseZMove(@Nonnull Pokemon user) {
        if (!Z_MOVE_REQUIRED_POKEMON.contains(user.getSpecies())) {
            return false;
        }

        return Z_MOVE_ITEM == user.ITEM.get() && !Collections.disjoint(Z_PRECURSOR_MOVES.get(), user.MOVES.get());
    }

    protected int use(Pokemon user, Pokemon target, Battle battle, Action action) {
        return target.damage(action);
    }

    protected int use(Pokemon user, Pokemon target, Battle battle, Action action, @Nonnull Integer repeats) {
        int damage = 0;
        for (int i = 0; i < repeats; i++) {
            damage += use(user, target, battle, action);
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

    protected void message(@Nonnull Pokemon user, @Nonnull Pokemon target, @Nonnull Action action) {

    }

    @Override
    public boolean hits(@Nonnull Pokemon target, @Nonnull Action action) {
        Pokemon attacker = action.getAttacker();
        Pokemon defender = action.getTarget();

        if (defender.TYPES.isImmune(action)) {
            return false;
        }

        if (ACCURACY == 0) {
            return true;
        }

        if (attacker.getBattle().getGeneration() == Generation.I) {
            if (Math.random() < 0.004) {
                return false;
            }
        }

        double attackerAccuracy = attacker.calculate(BattleStat.ACCURACY);
        double defenderEvasion = defender.calculate(BattleStat.EVASION);
        double probability = ACCURACY * (attackerAccuracy / defenderEvasion);
        if (probability > 1 || probability < Math.random()) {
            return true;
        }

        return false;
    }

    public boolean hitsWithoutDefenderStages(@Nonnull Action action) {
        Pokemon attacker = action.getAttacker();
        Pokemon defender = action.getTarget();

        if (defender.TYPES.isImmune(action)) {
            return false;
        }

        if (ACCURACY == 0) {
            return true;
        }

        if (attacker.getBattle().getGeneration() == Generation.I) {
            if (Math.random() < 0.004) {
                return false;
            }
        }

        double attackerAccuracy = attacker.calculate(BattleStat.ACCURACY);
        double probability = ACCURACY * attackerAccuracy;
        if (probability > 1 || probability < Math.random()) {
            return true;
        }

        return false;
    }

    public boolean hitsIgnoreTypes(@Nonnull Action action) {
        Pokemon attacker = action.getAttacker();
        Pokemon defender = action.getTarget();

        if (ACCURACY == 0) {
            return true;
        }

        if (attacker.getBattle().getGeneration() == Generation.I) {
            if (Math.random() < 0.004) {
                return false;
            }
        }

        double attackerAccuracy = attacker.calculate(BattleStat.ACCURACY);
        double defenderEvasion = defender.calculate(BattleStat.EVASION);
        double probability = ACCURACY * (attackerAccuracy / defenderEvasion);
        if (probability > 1 || probability < Math.random()) {
            return true;
        }

        return false;
    }

    protected int useAsZMove(Pokemon user, Pokemon target, Battle battle, Action action) {
        return use(user, target, battle, action);
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
