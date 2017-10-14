package com.github.drsmugbrain.pokemon.ability;

import com.github.drsmugbrain.pokemon.IModifier;
import com.github.drsmugbrain.pokemon.Pokemon;
import org.json.JSONArray;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.*;

/**
 * Created by DrSmugleaf on 07/06/2017.
 */
public enum Abilities implements IModifier {

    ADAPTABILITY("Adaptability"),
    AERILATE("Aerilate"),
    AFTERMATH("Aftermath"),
    AIR_LOCK("Air Lock"),
    ANALYTIC("Analytic"),
    ANGER_POINT("Anger Point"),
    ANTICIPATION("Anticipation"),
    ARENA_TRAP("Arena Trap"),
    AROMA_VEIL("Aroma Veil"),
    AURA_BREAK("Aura Break"),
    BAD_DREAMS("Bad Dreams"),
    BATTLE_ARMOR("Battle Armor"),
    BIG_PECKS("Big Pecks"),
    BLAZE("Blaze"),
    BULLETPROOF("Bulletproof"),
    CHEEK_POUCH("Cheek Pouch"),
    CHLOROPHYLL("Chlorophyll"),
    CLEAR_BODY("Clear Body"),
    CLOUD_NINE("Cloud Nine"),
    COLOR_CHANGE("Color Change"),
    COMPETITIVE("Competitive"),
    COMPOUND_EYES("Compound Eyes"),
    CONTRARY("Contrary"),
    CURSED_BODY("Cursed Body"),
    CUTE_CHARM("Cute Charm"),
    DAMP("Damp"),
    DARK_AURA("Dark Aura"),
    DEFEATIST("Defeatist"),
    DEFIANT("Defiant"),
    DELTA_STREAM("Delta Stream"),
    DESOLATE_LAND("Desolate Land"),
    DOWNLOAD("Download"),
    DRIZZLE("Drizzle"),
    DROUGHT("Drought"),
    DRY_SKIN("Dry Skin"),
    EARLY_BIRD("Early Bird"),
    EFFECT_SPORE("Effect Spore"),
    FAIRY_AURA("Fairy Aura"),
    FILTER("Filter"),
    FLAME_BODY("Flame Body"),
    FLARE_BOOST("Flare Boost"),
    FLASH_FIRE("Flash Fire"),
    FLOWER_GIFT("Flower Gift"),
    FLOWER_VEIL("Flower Veil"),
    FORECAST("Forecast"),
    FOREWARN("Forewarn"),
    FRIEND_GUARD("Friend Guard"),
    FRISK("Frisk"),
    FUR_COAT("Fur Coat"),
    GALE_WINGS("Gale Wings"),
    GLUTTONY("Gluttony"),
    GOOEY("Gooey"),
    GRASS_PELT("Grass Pelt"),
    GUTS("Guts"),
    HARVEST("Harvest"),
    HEALER("Healer"),
    HEATPROOF("Heatproof"),
    HEAVY_METAL("Heavy Metal"),
    HONEY_GATHER("Honey Gather"),
    HUGE_POWER("Huge Power"),
    HUSTLE("Hustle"),
    HYDRATION("Hydration"),
    HYPER_CUTTER("Hyper Cutter"),
    ICE_BODY("Ice Body"),
    ILLUMINATE("Illuminate"),
    ILLUSION("Illusion"),
    IMMUNITY("Immunity"),
    IMPOSTER("Imposter"),
    INFILTRATOR("Infiltrator"),
    INNER_FOCUS("Inner Focus"),
    INSOMNIA("Insomnia"),
    INTIMIDATE("Intimidate"),
    IRON_BARBS("Iron Barbs"),
    IRON_FIST("Iron Fist"),
    JUSTIFIED("Justified"),
    KEEN_EYE("Keen Eye"),
    KLUTZ("Klutz"),
    LEAF_GUARD("Leaf Guard"),
    LEVITATE("Levitate"),
    LIGHT_METAL("Light Metal"),
    LIGHTNING_ROD("Lightning Rod"),
    LIMBER("Limber"),
    LIQUID_OOZE("Liquid Ooze"),
    MAGIC_BOUNCE("Magic Bounce"),
    MAGIC_GUARD("Magic Guard"),
    MAGICIAN("Magician"),
    MAGMA_ARMOR("Magma Armor"),
    MAGNET_PULL("Magnet Pull"),
    MARVEL_SCALE("Marvel Scale"),
    MEGA_LAUNCHER("Mega Launcher"),
    MINUS("Minus"),
    MOLD_BREAKER("Mold Breaker"),
    MOODY("Moody"),
    MOTOR_DRIVE("Motor Drive"),
    MOXIE("Moxie"),
    MULTISCALE("Multiscale"),
    MULTITYPE("Multitype"),
    MUMMY("Mummy"),
    NATURAL_CURE("Natural Cure"),
    NO_GUARD("No Guard"),
    NORMALIZE("Normalize"),
    OBLIVIOUS("Oblivious"),
    OVERCOAT("Overcoat"),
    OVERGROW("Overgrow"),
    OWN_TEMPO("Own Tempo"),
    PARENTAL_BOND("Parental Bond"),
    PICKPOCKET("Pickpocket"),
    PICKUP("Pickup"),
    PIXILATE("Pixilate"),
    PLUS("Plus"),
    POISON_HEAL("Poison Heal"),
    POISON_POINT("Poison Point"),
    POISON_TOUCH("Poison Touch"),
    PRANKSTER("Prankster"),
    PRESSURE("Pressure"),
    PRIMORDIAL_SEA("Primordial Sea"),
    PROTEAN("Protean"),
    PURE_POWER("Pure Power"),
    QUICK_FEET("Quick Feet"),
    RAIN_DISH("Rain Dish"),
    RATTLED("Rattled"),
    RECKLESS("Reckless"),
    REFRIGERATE("Refrigerate"),
    REGENERATOR("Regenerator"),
    RIVALRY("Rivalry"),
    ROCK_HEAD("Rock Head"),
    ROUGH_SKIN("Rough Skin"),
    RUN_AWAY("Run Away"),
    SAND_FORCE("Sand Force"),
    SAND_RUSH("Sand Rush"),
    SAND_STREAM("Sand Stream"),
    SAND_VEIL("Sand Veil"),
    SAP_SIPPER("Sap Sipper"),
    SCRAPPY("Scrappy"),
    SERENE_GRACE("Serene Grace"),
    SHADOW_TAG("Shadow Tag"),
    SHED_SKIN("Shed Skin"),
    SHEER_FORCE("Sheer Force"),
    SHELL_ARMOR("Shell Armor"),
    SHIELD_DUST("Shield Dust"),
    SIMPLE("Simple"),
    SKILL_LINK("Skill Link"),
    SLOW_START("Slow Start"),
    SNIPER("Sniper"),
    SNOW_CLOAK("Snow Cloak"),
    SNOW_WARNING("Snow Warning"),
    SOLAR_POWER("Solar Power"),
    SOLID_ROCK("Solid Rock"),
    SOUNDPROOF("Soundproof"),
    SPEED_BOOST("Speed Boost"),
    STALL("Stall"),
    STANCE_CHANGE("Stance Change"),
    STATIC("Static"),
    STEADFAST("Steadfast"),
    STENCH("Stench"),
    STICKY_HOLD("Sticky Hold"),
    STORM_DRAIN("Storm Drain"),
    STRONG_JAW("Strong Jaw"),
    STURDY("Sturdy"),
    SUCTION_CUPS("Suction Cups"),
    SUPER_LUCK("Super Luck"),
    SWARM("Swarm"),
    SWEET_VEIL("Sweet Veil"),
    SWIFT_SWIM("Swift Swim"),
    SYMBIOSIS("Symbiosis"),
    SYNCHRONIZE("Synchronize"),
    TANGLED_FEET("Tangled Feet"),
    TECHNICIAN("Technician"),
    TELEPATHY("Telepathy"),
    TERAVOLT("Teravolt"),
    THICK_FAT("Thick Fat"),
    TINTED_LENS("Tinted Lens"),
    TORRENT("Torrent"),
    TOUGH_CLAWS("Tough Claws"),
    TOXIC_BOOST("Toxic Boost"),
    TRACE("Trace"),
    TRUANT("Truant"),
    TURBOBLAZE("Turboblaze"),
    UNAWARE("Unaware"),
    UNBURDEN("Unburden"),
    UNNERVE("Unnerve"),
    VICTORY_STAR("Victory Star"),
    VITAL_SPIRIT("Vital Spirit"),
    VOLT_ABSORB("Volt Absorb"),
    WATER_ABSORB("Water Absorb"),
    WATER_VEIL("Water Veil"),
    WEAK_ARMOR("Weak Armor"),
    WHITE_SMOKE("White Smoke"),
    WONDER_GUARD("Wonder Guard"),
    WONDER_SKIN("Wonder Skin"),
    ZEN_MODE("Zen Mode"),
    BATTERY("Battery"),
    WIMP_OUT("Wimp Out"),
    BEAST_BOOST("Beast Boost"),
    BERSERK("Berserk"),
    COMATOSE("Comatose"),
    CORROSION("Corrosion"),
    DANCER("Dancer"),
    DAZZLING("Dazzling"),
    DISGUISE("Disguise"),
    ELECTRIC_SURGE("Electric Surge"),
    EMERGENCY_EXIT("Emergency Exit"),
    FLUFFY("Fluffy"),
    FULL_METAL_BODY("Full Metal Body"),
    GALVANIZE("Galvanize"),
    GRASSY_SURGE("Grassy Surge"),
    INNARDS_OUT("Innards Out"),
    LIQUID_VOICE("Liquid Voice"),
    LONG_REACH("Long Reach"),
    MERCILESS("Merciless"),
    MISTY_SURGE("Misty Surge"),
    BATTLE_BOND("Battle Bond"),
    POWER_OF_ALCHEMY("Power of Alchemy"),
    PRISM_ARMOR("Prism Armor"),
    PSYCHIC_SURGE("Psychic Surge"),
    QUEENLY_MAJESTY("Queenly Majesty"),
    RECEIVER("Receiver"),
    RKS_SYSTEM("RKS System"),
    SCHOOLING("Schooling"),
    SHADOW_SHIELD("Shadow Shield"),
    SHIELDS_DOWN("Shields Down"),
    SLUSH_RUSH("Slush Rush"),
    SOUL_HEART("Soul-Heart"),
    STAKEOUT("Stakeout"),
    STAMINA("Stamina"),
    STEELWORKER("Steelworker"),
    SURGE_SURFER("Surge Surfer"),
    TANGLING_HAIR("Tangling Hair"),
    TRIAGE("Triage"),
    WATER_BUBBLE("Water Bubble"),
    WATER_COMPACTION("Water Compaction"),
    POWER_CONSTRUCT("Power Construct");

    private String NAME;
    private boolean suppressed = false;

    Abilities(@Nonnull String name) {
        Holder.MAP.put(name, this);
        this.NAME = name;
    }

    @Nonnull
    public static Abilities getAbility(@Nonnull String ability) {
        if(!Holder.MAP.containsKey(ability)) {
            throw new NullPointerException("Ability " + ability + " doesn't exist");
        }

        return Holder.MAP.get(ability);
    }

    @Nonnull
    public static Abilities[] getAbilities(@Nonnull JSONArray abilities) {
        List<Abilities> abilityList = new ArrayList<>();

        for (int i = 0; i < abilities.length(); i++) {
            Abilities ability = Abilities.getAbility(abilities.getString(i));
            abilityList.add(ability);
        }

        return abilityList.toArray(new Abilities[0]);
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onOwnSendOut(@Nonnull Pokemon pokemon) {
        pokemon.setAbilitySuppressed(false);
    }

    private static class Holder {
        static Map<String, Abilities> MAP = new HashMap<>();
    }

}
