package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by DrSmugleaf on 17/06/2017.
 */
public enum Movess {

    _10000000_VOLT_THUNDERBOLT("10,000,000 Volt Thunderbolt") {
        @Override
        protected void useAsZMove(@Nonnull Pokemon user, @Nonnull Pokemon target, Battle battle, Trainer trainer) {
            super.use(user, target);
        }
    },
    ABSORB("Absorb") {
        @Override
        protected void use(@Nonnull Pokemon user, @Nonnull Pokemon target) {
            super.use(user, target);
            user.heal(this.getDamage(user, target) / 2);
        }
    },
    ACCELEROCK("Accelerock"),
    ACID("Acid") {
        @Override
        protected void use(@Nonnull Pokemon user, @Nonnull Pokemon target) {
            super.use(user, target);
            if (Math.random() < this.EFFECT_RATE) {
                target.lowerStatStage(Stat.SPECIAL_DEFENSE, 1);
            }
        }
    },
    ACID_ARMOR("Acid Armor") {
        @Override
        public void useAsZMove(@Nonnull Pokemon user, @Nullable Pokemon target, Battle battle, Trainer trainer) {
            user.resetLoweredStats();
            this.use(user, null);
        }
    },
    ACID_DOWNPOUR("Acid Downpour"),
    ACID_SPRAY("Acid Spray"),
    ACROBATICS("Acrobatics") {
        @Override
        public int getPower(Pokemon attacker) {
            if (attacker.getItem() == null) {
                return this.POWER * 2;
            } else {
                return this.POWER;
            }
        }
    },
    ACUPRESSURE("Acupressure") {
        @Override
        protected void useAsZMove(@Nonnull Pokemon user, @Nullable Pokemon target, Battle battle, Trainer trainer) {
            user.raiseCriticalHitStage(1);
            super.useAsZMove(user, target, battle, trainer);
        }
    },
    AERIAL_ACE("Aerial Ace"), // Always hit except semi invulnerable targets
    AEROBLAST("Aeroblast"),
    AFTER_YOU("After You") {
        @Override
        protected void use(@Nonnull Pokemon user, Pokemon target, @Nonnull Battle battle, Trainer trainer) {
            if (battle.TURN_ORDER.contains(target) && battle.TURN_ORDER.indexOf(target) > battle.TURN_ORDER.indexOf(user)) {
                battle.TURN_ORDER.add(battle.TURN_ORDER.indexOf(user) + 1, battle.TURN_ORDER.remove(battle.TURN_ORDER.indexOf(target)));
            }
            super.use(user, target);
        }

        @Override
        protected void useAsZMove(@Nonnull Pokemon user, Pokemon target, Battle battle, Trainer trainer) {
            user.raiseStatStage(Stat.SPEED, 1);
            super.useAsZMove(user, target, battle, trainer);
        }
    },
    AGILITY("Agility") {
        @Override
        public void useAsZMove(@Nonnull Pokemon user, @Nullable Pokemon target, @Nullable Battle battle, Trainer trainer) {
            user.resetLoweredStats();
            this.use(user, null);
        }
    },
    AIR_CUTTER("Air Cutter"),
    AIR_SLASH("Air Slash"),
    ALL_OUT_PUMMELING("All-Out Pummeling"),
    ALLY_SWITCH("Ally Switch") {
        @Override
        protected void use(@Nonnull Pokemon user, @Nonnull Pokemon target, @Nonnull Battle battle, @Nonnull Trainer trainer) {
            trainer.swapActivePokemon(user, target);
            super.use(user, target, battle, trainer);
        }

        @Override
        protected void useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, Trainer trainer) {
            user.raiseStatStage(Stat.SPEED, 2);
            this.use(user, target, battle, trainer);
        }
    },
    AMNESIA("Amnesia") {
        @Override
        public void useAsZMove(@Nonnull Pokemon user, @Nullable Pokemon target, @Nullable Battle battle, Trainer trainer) {
            user.resetLoweredStats();
            this.use(user, null);
        }
    },
    ANCHOR_SHOT("Anchor Shot"),
    ANCIENT_POWER("Ancient Power"),
    AQUA_JET("Aqua Jet"),
    AQUA_RING("Aqua Ring"),
    AQUA_TAIL("Aqua Tail"),
    ARM_THRUST("Arm Thrust"),
    AROMATHERAPY("Aromatherapy"),
    AROMATIC_MIST("Aromatic Mist"),
    ASSIST("Assist"),
    ASSURANCE("Assurance"),
    ASTONISH("Astonish"),
    ATTACK_ORDER("Attack Order"),
    ATTRACT("Attract"),
    AURA_SPHERE("Aura Sphere"),
    AURORA_BEAM("Aurora Beam"),
    AURORA_VEIL("Aurora Veil"),
    AUTOTOMIZE("Autotomize"),
    AVALANCHE("Avalanche"),
    BABY_DOLL_EYES("Baby-Doll Eyes"),
    BANEFUL_BUNKER("Baneful Bunker"),
    BARRAGE("Barrage"),
    BARRIER("Barrier"),
    BATON_PASS("Baton Pass"),
    BEAK_BLAST("Beak Blast"),
    BEAT_UP("Beat Up"),
    BELCH("Belch"),
    BELLY_DRUM("Belly Drum"),
    BESTOW("Bestow"),
    BIDE("Bide"),
    BIND("Bind"),
    BITE("Bite"),
    BLACK_HOLE_ECLIPSE("Black Hole Eclipse"),
    BLAST_BURN("Blast Burn"),
    BLAZE_KICK("Blaze Kick"),
    BLIZZARD("Blizzard"),
    BLOCK("Block"),
    BLOOM_DOOM("Bloom Doom"),
    BLUE_FLARE("Blue Flare"),
    BODY_SLAM("Body Slam"),
    BOLT_STRIKE("Bolt Strike"),
    BONE_CLUB("Bone Club"),
    BONE_RUSH("Bone Rush"),
    BONEMERANG("Bonemerang"),
    BOOMBURST("Boomburst"),
    BOUNCE("Bounce"),
    BRAVE_BIRD("Brave Bird"),
    BREAKNECK_BLITZ("Breakneck Blitz"),
    BRICK_BREAK("Brick Break"),
    BRINE("Brine"),
    BRUTAL_SWING("Brutal Swing"),
    BUBBLE("Bubble"),
    BUBBLE_BEAM("Bubble Beam"),
    BUG_BITE("Bug Bite"),
    BUG_BUZZ("Bug Buzz"),
    BULK_UP("Bulk Up"),
    BULLDOZE("Bulldoze"),
    BULLET_PUNCH("Bullet Punch"),
    BULLET_SEED("Bullet Seed"),
    BURN_UP("Burn Up"),
    CALM_MIND("Calm Mind"),
    CAMOUFLAGE("Camouflage"),
    CAPTIVATE("Captivate"),
    CATASTROPIKA("Catastropika"),
    CELEBRATE("Celebrate"),
    CHARGE("Charge"),
    CHARGE_BEAM("Charge Beam"),
    CHARM("Charm"),
    CHATTER("Chatter"),
    CHIP_AWAY("Chip Away"),
    CIRCLE_THROW("Circle Throw"),
    CLAMP("Clamp"),
    CLANGING_SCALES("Clanging Scales"),
    CLEAR_SMOG("Clear Smog"),
    CLOSE_COMBAT("Close Combat"),
    COIL("Coil"),
    COMET_PUNCH("Comet Punch"),
    CONFIDE("Confide"),
    CONFUSE_RAY("Confuse Ray"),
    CONFUSION("Confusion"),
    CONSTRICT("Constrict"),
    CONTINENTAL_CRUSH("Continental Crush"),
    CONVERSION("Conversion"),
    CONVERSION_2("Conversion 2"),
    COPYCAT("Copycat"),
    CORE_ENFORCER("Core Enforcer"),
    CORKSCREW_CRASH("Corkscrew Crash"),
    COSMIC_POWER("Cosmic Power"),
    COTTON_GUARD("Cotton Guard"),
    COTTON_SPORE("Cotton Spore"),
    COUNTER("Counter"),
    COVET("Covet"),
    CRABHAMMER("Crabhammer"),
    CRAFTY_SHIELD("Crafty Shield"),
    CROSS_CHOP("Cross Chop"),
    CROSS_POISON("Cross Poison"),
    CRUNCH("Crunch"),
    CRUSH_CLAW("Crush Claw"),
    CRUSH_GRIP("Crush Grip"),
    CURSE("Curse"),
    CUT("Cut"),
    DARK_PULSE("Dark Pulse"),
    DARK_VOID("Dark Void"),
    DARKEST_LARIAT("Darkest Lariat"),
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
    FLAME_WHEEL("Flame Wheel"),
    FLAMETHROWER("Flamethrower"),
    FLARE_BLITZ("Flare Blitz"),
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
    FUSION_FLARE("Fusion Flare"),
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
    HAZE("Haze"),
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
    SACRED_FIRE("Sacred Fire"),
    SACRED_SWORD("Sacred Sword"),
    SAFEGUARD("Safeguard"),
    SAND_ATTACK("Sand Attack"),
    SAND_TOMB("Sand Tomb"),
    SANDSTORM("Sandstorm"),
    SAVAGE_SPIN_OUT("Savage Spin-Out"),
    SCALD("Scald"),
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
    STEAM_ERUPTION("Steam Eruption"),
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
    TRI_ATTACK("Tri Attack"),
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
    private final List<String> Z_MOVE_REQUIRED_POKEMON = new ArrayList<>();
    private final List<Movess> Z_MOVE_REQUIRED_MOVES = new ArrayList<>();
    private final List<Status> STATUS_EFFECTS = new ArrayList<>();
    private final Map<VolatileStatus, Integer> VOLATILE_STATUS_EFFECTS_CHANCE = new HashMap<>();
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
    private int PP;
    private int ACCURACY;
    private boolean IS_Z_MOVE = false;
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
    private Movess CORRESPONDING_Z_MOVE;
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

    Movess(@Nonnull String name) {
        Holder.MAP.put(name.toLowerCase(), this);
        this.NAME = name;
    }

    public static Movess getMove(@Nonnull String move) {
        move = move.toLowerCase();
        if (!Holder.MAP.containsKey(move)) {
            throw new NullPointerException("Move " + move + " doesn't exist");
        }

        return Holder.MAP.get(move);
    }

    protected Movess setType(@Nonnull Type type) {
        this.TYPE = type;
        return this;
    }

    protected Movess setPP(int pp) {
        this.PP = pp;
        return this;
    }

    protected Movess setPower(int power) {
        this.POWER = power;
        return this;
    }

    protected Movess setAccuracy(int accuracy) {
        this.ACCURACY = accuracy;
        return this;
    }

    protected Movess setIsZMove(boolean bool) {
        this.IS_Z_MOVE = bool;
        return this;
    }

    protected Movess setIsSelfZMove(boolean bool) {
        this.IS_SELF_Z_MOVE = bool;
        return this;
    }

    protected Movess setZMoveItem(@Nullable Item item) {
        this.Z_MOVE_ITEM = item;
        return this;
    }

    protected Movess setZMoveRequiredPokemon(@Nullable String... pokemon) {
        if (pokemon != null) {
            this.Z_MOVE_REQUIRED_POKEMON.addAll(Arrays.asList(pokemon));
        }
        return this;
    }

    protected Movess setZMoveRequiredMove(@Nullable Movess move) {
        this.Z_MOVE_REQUIRED_MOVES.add(move);
        return this;
    }

    protected Movess setBattleEffect(@Nonnull String battleEffect) {
        this.BATTLE_EFFECT = battleEffect;
        return this;
    }

    protected Movess setInDepthEffect(@Nullable String inDepthEffect) {
        this.IN_DEPTH_EFFECT = inDepthEffect;
        return this;
    }

    protected Movess setSecondaryEffect(@Nonnull String secondaryEffect) {
        this.SECONDARY_EFFECT = secondaryEffect;
        return this;
    }

    protected Movess setEffectRate(@Nullable Integer effectRate) {
        this.EFFECT_RATE = effectRate;
        return this;
    }

    protected Movess setStatusEffects(@Nonnull Status... statuses) {
        this.STATUS_EFFECTS.addAll(Arrays.asList(statuses));
        return this;
    }

    protected Movess setVolatileStatusEffect(int chance, @Nonnull VolatileStatus status) {
        this.VOLATILE_STATUS_EFFECTS_CHANCE.put(status, chance);
        return this;
    }

    protected Movess setVolatileStatusEffect(@Nonnull VolatileStatus status) {
        this.VOLATILE_STATUS_EFFECTS_CHANCE.put(status, this.EFFECT_RATE);
        return this;
    }

    protected Movess setRaisesOwnStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.RAISES_OWN_STATS.put(stat, amount);
        }
        return this;
    }

    protected Movess setLowersOwnStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.LOWERS_OWN_STATS.put(stat, amount);
        }
        return this;
    }

    protected Movess setRaisesOwnRandomStat(int amount) {
        this.RAISES_OWN_RANDOM_STAT = amount;
        return this;
    }

    protected Movess setLowersOwnRandomStat(int amount) {
        this.LOWERS_OWN_RANDOM_STAT = amount;
        return this;
    }

    protected Movess setRaisesEnemyStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.RAISES_ENEMY_STATS.put(stat, amount);
        }
        return this;
    }

    protected Movess setLowersEnemyStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.LOWERS_ENEMY_STATS.put(stat, amount);
        }
        return this;
    }

    protected Movess setRaisesEnemyRandomStat(int amount) {
        this.RAISES_ENEMY_RANDOM_STAT = amount;
        return this;
    }

    protected Movess setLowersEnemyRandomStat(int amount) {
        this.LOWERS_ENEMY_RANDOM_STAT = amount;
        return this;
    }

    protected Movess setRaisesTargetStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.RAISES_TARGET_STATS.put(stat, amount);
        }
        return this;
    }

    protected Movess setLowersTargetStats(int amount, @Nonnull Stat... stats) {
        for (Stat stat : stats) {
            this.LOWERS_TARGET_STATS.put(stat, amount);
        }
        return this;
    }

    protected Movess setRaisesTargetRandomStat(int amount) {
        this.RAISES_TARGET_RANDOM_STAT = amount;
        return this;
    }

    protected Movess setLowersTargetRandomStat(int amount) {
        this.LOWERS_TARGET_RANDOM_STAT = amount;
        return this;
    }

    protected Movess setCorrespondingZMove(@Nullable Movess correspondingZMove) {
        this.CORRESPONDING_Z_MOVE = correspondingZMove;
        return this;
    }

    protected Movess setZMovePower(@Nullable Integer power) {
        this.Z_MOVE_POWER = power;
        return this;
    }

    protected Movess setDetailedEffect(@Nullable String detailedEffect) {
        this.DETAILED_EFFECT = detailedEffect;
        return this;
    }

    protected Movess setPriority(int priority) {
        this.PRIORITY = priority;
        return this;
    }

    protected Movess setTarget(@Nullable Target target) {
        this.TARGET = target;
        return this;
    }

    protected Movess setPokemonHit(@Nonnull Hit hit) {
        this.POKEMON_HIT = hit;
        return this;
    }

    protected Movess setPhysicalContact(boolean bool) {
        this.PHYSICAL_CONTACT = bool;
        return this;
    }

    protected Movess setSoundType(boolean bool) {
        this.SOUND_TYPE = bool;
        return this;
    }

    protected Movess setPunchMove(boolean bool) {
        this.PUNCH_MOVE = bool;
        return this;
    }

    protected Movess setSnatchable(boolean bool) {
        this.SNATCHABLE = bool;
        return this;
    }

    protected Movess setZMove(boolean bool) {
        this.Z_MOVE = bool;
        return this;
    }

    protected Movess setDefrostsWhenUsed(boolean bool) {
        this.DEFROSTS_WHEN_USED = bool;
        return this;
    }

    protected Movess setHitsOppositeSideInTriples(boolean bool) {
        this.HITS_OPPOSITE_SIDE_IN_TRIPLES = bool;
        return this;
    }

    protected Movess setReflected(boolean bool) {
        this.REFLECTED = bool;
        return this;
    }

    protected Movess setBlocked(boolean bool) {
        this.BLOCKED = bool;
        return this;
    }

    protected Movess setCopyable(boolean bool) {
        this.COPYABLE = bool;
        return this;
    }

    public String getName() {
        return this.NAME;
    }

    public int getBasePower() {
        return this.POWER;
    }

    public int getPower(Pokemon attacker) {
        return this.POWER;
    }

    public int getPowerOfZMove(Movess originalMove) throws IllegalZMoveException {
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

    public Category getCategory() {
        return this.CATEGORY;
    }

    protected Movess setCategory(@Nonnull Category category) {
        this.CATEGORY = category;
        return this;
    }

    public Category getCategoryOfZmove(Movess originalMove) {
        return originalMove.getCategory();
    }

    public CriticalHitStage getBaseCriticalHitRate() {
        return this.BASE_CRITICAL_HIT_RATE;
    }

    protected Movess setBaseCriticalHitRate(@Nullable CriticalHitStage baseCriticalHitRate) {
        this.BASE_CRITICAL_HIT_RATE = baseCriticalHitRate;
        return this;
    }

    protected int getDamage(@Nonnull Pokemon attacker, @Nonnull Pokemon defender) {
        int attackStat;
        int defenseStat;
        if (this.CATEGORY == Category.PHYSICAL) {
            attackStat = attacker.getStat(Stat.ATTACK);
            defenseStat = defender.getStat(Stat.DEFENSE);
        } else if (this.CATEGORY == Category.SPECIAL) {
            attackStat = attacker.getStat(Stat.SPECIAL_ATTACK);
            defenseStat = defender.getStat(Stat.SPECIAL_DEFENSE);
        } else {
            return 0;
        }
        int level = attacker.getLevel();
        int attackPower = this.POWER;
        double stabMultiplier = attacker.getStabMultiplier();
        double effectiveness = Type.getDamageMultiplier(defender.getTypes(), this.TYPE);
        int randomNumber = (int) (Math.random() * 100 + 85);
        return (int) (((((2 * level / 5 + 2) * attackStat * attackPower / defenseStat) / 50) + 2) * stabMultiplier * effectiveness * randomNumber / 100);
    }

    protected void use(@Nonnull Pokemon user, Pokemon target) {
        if(target != null && this.POWER > 0) {
            target.damage(this.getDamage(user, target));
        }
    }

    protected void use(@Nonnull Pokemon user, Pokemon target, @Nonnull Battle battle, Trainer trainer) {
        this.use(user, target);
    }

    public boolean canUseZMove(@Nonnull Pokemon pokemon) {
        if (!this.Z_MOVE_REQUIRED_POKEMON.isEmpty() && !this.Z_MOVE_REQUIRED_POKEMON.contains(pokemon.getName())) {
            return false;
        }

        return this.Z_MOVE_ITEM == pokemon.getItem() && !Collections.disjoint(this.Z_MOVE_REQUIRED_MOVES, pokemon.getMoves());
    }

    protected void useAsZMove(@Nonnull Pokemon user, Pokemon target, @Nullable Battle battle, @Nullable Trainer trainer) {
        user.removeItem();
        if (battle == null) {
            this.use(user, target);
            return;
        }
        this.use(user, target, battle, trainer);
    }

    private static class Holder {
        static Map<String, Movess> MAP = new HashMap<>();
    }

}
