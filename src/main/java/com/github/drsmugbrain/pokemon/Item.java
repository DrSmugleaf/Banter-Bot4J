package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 09/06/2017.
 */
public enum Item {

    ABOMASITE("Abomasite", ItemCategory.MEGA_STONE),
    ABSOLITE("Absolite", ItemCategory.MEGA_STONE),
    ZOOM_LENS("Zoom Lens"),
    ABSORB_BULB("Absorb Bulb"),
    ADAMANT_ORB("Adamant Orb"),
    AERODACTYLITE("Aerodactylite", ItemCategory.MEGA_STONE),
    AGGRONITE("Aggronite", ItemCategory.MEGA_STONE),
    AGUAV_BERRY("Aguav Berry"),
    AIR_BALLOON("Air Balloon"),
    ALAKAZITE("Alakazite", ItemCategory.MEGA_STONE),
    ALTARIANITE("Altarianite", ItemCategory.MEGA_STONE),
    AMPHAROSITE("Ampharosite", ItemCategory.MEGA_STONE),
    APICOT_BERRY("Apicot Berry"),
    ASPEAR_BERRY("Aspear Berry"),
    ASSAULT_VEST("Assault Vest"),
    AUDINITE("Audinite", ItemCategory.MEGA_STONE),
    BABIRI_BERRY("Babiri Berry"),
    BANETTITE("Banettite", ItemCategory.MEGA_STONE),
    BEEDRILLITE("Beedrillite", ItemCategory.MEGA_STONE),
    BERRY("Berry"),
    BERRY_JUICE("Berry Juice"),
    BERSERK_GENE("Berserk Gene"),
    BIG_ROOT("Big Root"),
    BINDING_BAND("Binding Band"),
    BITTER_BERRY("Bitter Berry"),
    BLACK_BELT("Black Belt"),
    BLACK_SLUDGE("Black Sludge"),
    BLACK_GLASSES("Black Glasses"),
    BLASTOISINITE("Blastoisinite", ItemCategory.MEGA_STONE),
    BLAZIKENITE("Blazikenite", ItemCategory.MEGA_STONE),
    BLUE_ORB("Blue Orb", ItemCategory.PRIMAL_ORB),
    BRIGHT_POWDER("Bright Powder"),
    BUG_GEM("Bug Gem"),
    BURN_DRIVE("Burn Drive", ItemCategory.GENESECT_DRIVE),
    CAMERUPTITE("Cameruptite", ItemCategory.MEGA_STONE),
    CELL_BATTERY("Cell Battery"),
    CHARCOAL("Charcoal"),
    CHARIZARDITE_X("Charizardite X", ItemCategory.MEGA_STONE),
    CHARIZARDITE_Y("Charizardite Y", ItemCategory.MEGA_STONE),
    CHARTI_BERRY("Charti Berry"),
    CHERI_BERRY("Cheri Berry"),
    CHESTO_BERRY("Chesto Berry"),
    CHILAN_BERRY("Chilan Berry"),
    CHILL_DRIVE("Chill Drive", ItemCategory.GENESECT_DRIVE),
    CHOICE_BAND("Choice Band"),
    CHOICE_SCARF("Choice Scarf"),
    CHOICE_SPECS("Choice Specs"),
    CHOPLE_BERRY("Chople Berry"),
    CLEANSE_TAG("Cleanse Tag"),
    COBA_BERRY("Coba Berry"),
    COLBUR_BERRY("Colbur Berry"),
    CUSTAP_BERRY("Custap Berry"),
    DAMP_ROCK("Damp Rock"),
    DARK_GEM("Dark Gem"),
    DEEP_SEA_SCALE("Deep Sea Scale"),
    DEEP_SEA_TOOTH("Deep Sea Tooth"),
    DESTINY_KNOT("Destiny Knot"),
    DIANCITE("Diancite", ItemCategory.MEGA_STONE),
    DOUSE_DRIVE("Douse Drive", ItemCategory.GENESECT_DRIVE),
    DRACO_PLATE("Draco Plate", ItemCategory.ARCEUS_PLATE),
    DRAGON_FANG("Dragon Fang"),
    DRAGON_GEM("Dragon Gem"),
    DREAD_PLATE("Dread Plate", ItemCategory.ARCEUS_PLATE),
    EARTH_PLATE("Earth Plate", ItemCategory.ARCEUS_PLATE),
    EJECT_BUTTON("Eject Button"),
    ELECTRIC_GEM("Electric Gem"),
    ENIGMA_BERRY("Enigma Berry"),
    EVIOLITE("Eviolite"),
    EXPERT_BELT("Expert Belt"),
    FAIRY_GEM("Fairy Gem"),
    FIGHTING_GEM("Fighting Gem"),
    FIGY_BERRY("Figy Berry"),
    FIRE_GEM("Fire Gem"),
    FIST_PLATE("Fist Plate", ItemCategory.ARCEUS_PLATE),
    FLAME_ORB("Flame Orb", ItemCategory.PRIMAL_ORB),
    FLAME_PLATE("Flame Plate", ItemCategory.ARCEUS_PLATE),
    FLOAT_STONE("Float Stone"),
    FLYING_GEM("Flying Gem"),
    FOCUS_BAND("Focus Band"),
    FOCUS_SASH("Focus Sash"),
    FULL_INCENSE("Full Incense"),
    GALLADITE("Galladite", ItemCategory.MEGA_STONE),
    GANLON_BERRY("Ganlon Berry"),
    GARCHOMPITE("Garchompite", ItemCategory.MEGA_STONE),
    GARDEVOIRITE("Gardevoirite", ItemCategory.MEGA_STONE),
    GENGARITE("Gengarite", ItemCategory.MEGA_STONE),
    GHOST_GEM("Ghost Gem"),
    GLALITITE("Glalitite", ItemCategory.MEGA_STONE),
    GOLD_BERRY("Gold Berry"),
    GRASS_GEM("Grass Gem"),
    GRIP_CLAW("Grip Claw"),
    GRISEOUS_ORB("Griseous Orb"),
    GROUND_GEM("Ground Gem"),
    GYARADOSITE("Gyaradosite", ItemCategory.MEGA_STONE),
    HABAN_BERRY("Haban Berry"),
    HARD_STONE("Hard Stone"),
    HEAT_ROCK("Heat Rock"),
    HERACRONITE("Heracronite", ItemCategory.MEGA_STONE),
    HOUNDOOMINITE("Houndoominite", ItemCategory.MEGA_STONE),
    IAPAPA_BERRY("Iapapa Berry"),
    ICE_BERRY("Ice Berry"),
    ICE_GEM("Ice Gem"),
    ICICLE_PLATE("Icicle Plate", ItemCategory.ARCEUS_PLATE),
    ICY_ROCK("Icy Rock"),
    INSECT_PLATE("Insect Plate", ItemCategory.ARCEUS_PLATE),
    IRON_BALL("Iron Ball"),
    IRON_PLATE("Iron Plate", ItemCategory.ARCEUS_PLATE),
    JABOCA_BERRY("Jaboca Berry"),
    KANGASKHANITE("Kangaskhanite", ItemCategory.MEGA_STONE),
    KASIB_BERRY("Kasib Berry"),
    KEBIA_BERRY("Kebia Berry"),
    KEE_BERRY("Kee Berry"),
    KELPSY_BERRY("Kelpsy Berry"),
    KINGS_ROCK("King's Rock"),
    LAGGING_TAIL("Lagging Tail"),
    LANSAT_BERRY("Lansat Berry"),
    LATIASITE("Latiasite", ItemCategory.MEGA_STONE),
    LATIOSITE("Latiosite", ItemCategory.MEGA_STONE),
    LAX_INCENSE("Lax Incense"),
    LEFTOVERS("Leftovers"),
    LEPPA_BERRY("Leppa Berry"),
    LIECHI_BERRY("Liechi Berry"),
    LIFE_ORB("Life Orb"),
    LIGHT_BALL("Light Ball"),
    LIGHT_CLAY("Light Clay"),
    LOPUNNITE("Lopunnite", ItemCategory.MEGA_STONE),
    LUCARIONITE("Lucarionite", ItemCategory.MEGA_STONE),
    LUCKY_PUNCH("Lucky Punch"),
    LUM_BERRY("Lum Berry"),
    LUMINOUS_MOSS("Luminous Moss"),
    LUSTROUS_ORB("Lustrous Orb"),
    MACHO_BRACE("Macho Brace"),
    MAGMARIZER("Magmarizer"),
    MAGNET("Magnet"),
    MAGO_BERRY("Mago Berry"),
    MAIL("Mail"),
    MANECTITE("Manectite", ItemCategory.MEGA_STONE),
    MARANGA_BERRY("Maranga Berry"),
    MAWILITE("Mawilite", ItemCategory.MEGA_STONE),
    MEADOW_PLATE("Meadow Plate", ItemCategory.ARCEUS_PLATE),
    MEDICHAMITE("Medichamite", ItemCategory.MEGA_STONE),
    MENTAL_HERB("Mental Herb"),
    METAGROSSITE("Metagrossite", ItemCategory.MEGA_STONE),
    METAL_COAT("Metal Coat"),
    METAL_POWDER("Metal Powder"),
    METRONOME("Metronome"),
    MEWTWONITE_X("Mewtwonite X", ItemCategory.MEGA_STONE),
    MEWTWONITE_Y("Mewtwonite Y", ItemCategory.MEGA_STONE),
    MICLE_BERRY("Micle Berry"),
    MIND_PLATE("Mind Plate", ItemCategory.ARCEUS_PLATE),
    MINT_BERRY("Mint Berry"),
    MIRACLE_BERRY("Miracle Berry"),
    MIRACLE_SEED("Miracle Seed"),
    MUSCLE_BAND("Muscle Band"),
    MYSTERY_BERRY("Mystery Berry"),
    MYSTIC_WATER("Mystic Water"),
    NEVER_MELT_ICE("Never-Melt Ice"),
    NORMAL_GEM("Normal Gem"),
    OCCA_BERRY("Occa Berry"),
    ODD_INCENSE("Odd Incense"),
    ORAN_BERRY("Oran Berry"),
    PRZCURE_BERRY("PRZCure Berry"),
    PSNCURE_BERRY("PSNCure Berry"),
    PASSHO_BERRY("Passho Berry"),
    PAYAPA_BERRY("Payapa Berry"),
    PECHA_BERRY("Pecha Berry"),
    PERSIM_BERRY("Persim Berry"),
    PETAYA_BERRY("Petaya Berry"),
    PIDGEOTITE("Pidgeotite", ItemCategory.MEGA_STONE),
    PINK_BOW("Pink Bow"),
    PINSIRITE("Pinsirite", ItemCategory.MEGA_STONE),
    PIXIE_PLATE("Pixie Plate", ItemCategory.ARCEUS_PLATE),
    POISON_BARB("Poison Barb"),
    POISON_GEM("Poison Gem"),
    POLKA_DOT_BOW("Polka Dot Bow"),
    POWER_ANKLET("Power Anklet"),
    POWER_BAND("Power Band"),
    POWER_BELT("Power Belt"),
    POWER_BRACER("Power Bracer"),
    POWER_HERB("Power Herb"),
    POWER_LENS("Power Lens"),
    POWER_WEIGHT("Power Weight"),
    PSYCHIC_GEM("Psychic Gem"),
    QUICK_CLAW("Quick Claw"),
    QUICK_POWDER("Quick Powder"),
    RAWST_BERRY("Rawst Berry"),
    RAZOR_CLAW("Razor Claw"),
    RAZOR_FANG("Razor Fang"),
    RED_CARD("Red Card"),
    RED_ORB("Red Orb"),
    RINDO_BERRY("Rindo Berry"),
    ROCK_GEM("Rock Gem"),
    ROCK_INCENSE("Rock Incense"),
    ROCKY_HELMET("Rocky Helmet"),
    ROSE_INCENSE("Rose Incense"),
    ROSELI_BERRY("Roseli Berry"),
    ROWAP_BERRY("Rowap Berry"),
    SABLENITE("Sablenite", ItemCategory.MEGA_STONE),
    SALAMENCITE("Salamencite", ItemCategory.MEGA_STONE),
    SAFETY_GOGGLES("Safety Goggles"),
    SALAC_BERRY("Salac Berry"),
    SCEPTILITE("Sceptilite", ItemCategory.MEGA_STONE),
    SCIZORITE("Scizorite", ItemCategory.MEGA_STONE),
    SCOPE_LENS("Scope Lens"),
    SEA_INCENSE("Sea Incense"),
    SHARP_BEAK("Sharp Beak"),
    SHARPEDONITE("Sharpedonite", ItemCategory.MEGA_STONE),
    SHED_SHELL("Shed Shell"),
    SHELL_BELL("Shell Bell"),
    SHOCK_DRIVE("Shock Drive", ItemCategory.GENESECT_DRIVE),
    SHUCA_BERRY("Shuca Berry"),
    SILK_SCARF("Silk Scarf"),
    SILVER_POWDER("Silver Powder"),
    SITRUS_BERRY("Sitrus Berry"),
    SKY_PLATE("Sky Plate", ItemCategory.ARCEUS_PLATE),
    SLOWBRONITE("Slowbronite", ItemCategory.MEGA_STONE),
    SMOOTH_ROCK("Smooth Rock"),
    SNOWBALL("Snowball"),
    SOFT_SAND("Soft Sand"),
    SOUL_DEW("Soul Dew"),
    SPELL_TAG("Spell Tag"),
    SPLASH_PLATE("Splash Plate", ItemCategory.ARCEUS_PLATE),
    SPOOKY_PLATE("Spooky Plate", ItemCategory.ARCEUS_PLATE),
    STARF_BERRY("Starf Berry"),
    STEEL_GEM("Steel Gem"),
    STEELIXITE("Steelixite", ItemCategory.MEGA_STONE),
    STICK("Stick"),
    STICKY_BARB("Sticky Barb"),
    STONE_PLATE("Stone Plate", ItemCategory.ARCEUS_PLATE),
    SWAMPERTITE("Swampertite", ItemCategory.MEGA_STONE),
    TANGA_BERRY("Tanga Berry"),
    THICK_CLUB("Thick Club"),
    TOXIC_ORB("Toxic Orb"),
    TOXIC_PLATE("Toxic Plate", ItemCategory.ARCEUS_PLATE),
    TWISTED_SPOON("Twisted Spoon"),
    TYRANITARITE("Tyranitarite", ItemCategory.MEGA_STONE),
    VENUSAURITE("Venusaurite", ItemCategory.MEGA_STONE),
    WACAN_BERRY("Wacan Berry"),
    WATER_GEM("Water Gem"),
    WATER_STONE("Water Stone"),
    WATMEL_BERRY("Watmel Berry"),
    WAVE_INCENSE("Wave Incense"),
    WEAKNESS_POLICY("Weakness Policy"),
    WHITE_HERB("White Herb"),
    WIDE_LENS("Wide Lens"),
    WIKI_BERRY("Wiki Berry"),
    WISE_GLASSES("Wise Glasses"),
    YACHE_BERRY("Yache Berry"),
    ZAP_PLATE("Zap Plate", ItemCategory.ARCEUS_PLATE),
    NORMALIUM_Z("Normalium Z", ItemCategory.Z_CRYSTAL),
    FIRIUM_Z("Firium Z", ItemCategory.Z_CRYSTAL),
    WATERIUM_Z("Waterium Z", ItemCategory.Z_CRYSTAL),
    ELECTRIUM_Z("Electrium Z", ItemCategory.Z_CRYSTAL),
    GRASSIUM_Z("Grassium Z", ItemCategory.Z_CRYSTAL),
    ICIUM_Z("Icium Z", ItemCategory.Z_CRYSTAL),
    FIGHTINIUM_Z("Fightinium Z", ItemCategory.Z_CRYSTAL),
    POISONIUM_Z("Poisonium Z", ItemCategory.Z_CRYSTAL),
    GROUNDIUM_Z("Groundium Z", ItemCategory.Z_CRYSTAL),
    FLYINIUM_Z("Flyinium Z", ItemCategory.Z_CRYSTAL),
    PSYCHIUM_Z("Psychium Z", ItemCategory.Z_CRYSTAL),
    BUGINIUM_Z("Buginium Z", ItemCategory.Z_CRYSTAL),
    ROCKIUM_Z("Rockium Z", ItemCategory.Z_CRYSTAL),
    GHOSTIUM_Z("Ghostium Z", ItemCategory.Z_CRYSTAL),
    DRAGONIUM_Z("Dragonium Z", ItemCategory.Z_CRYSTAL),
    DARKINIUM_Z("Darkinium Z", ItemCategory.Z_CRYSTAL),
    STEELIUM_Z("Steelium Z", ItemCategory.Z_CRYSTAL),
    FAIRIUM_Z("Fairium Z", ItemCategory.Z_CRYSTAL),
    PIKANIUM_Z("Pikanium Z", ItemCategory.Z_CRYSTAL),
    DECIDIUM_Z("Decidium Z", ItemCategory.Z_CRYSTAL),
    INCINIUM_Z("Incinium Z", ItemCategory.Z_CRYSTAL),
    PRIMARIUM_Z("Primarium Z", ItemCategory.Z_CRYSTAL),
    TAPUNIUM_Z("Tapunium Z", ItemCategory.Z_CRYSTAL),
    MARSHADIUM_Z("Marshadium Z", ItemCategory.Z_CRYSTAL),
    ALORAICHIUM_Z("Aloraichium Z", ItemCategory.Z_CRYSTAL),
    SNORLIUM_Z("Snorlium Z", ItemCategory.Z_CRYSTAL),
    EEVIUM_Z("Eevium Z", ItemCategory.Z_CRYSTAL),
    MEWNIUM_Z("Mewnium Z", ItemCategory.Z_CRYSTAL),
    PIKASHUNIUM_Z("Pikashunium Z", ItemCategory.Z_CRYSTAL),
    ADRENALINE_ORB("Adrenaline Orb"),
    BEAST_BALL("Beast Ball"),
    TERRAIN_EXTENDER("Terrain Extender"),
    PROTECTIVE_PADS("Protective Pads"),
    ELECTRIC_SEED("Electric Seed"),
    PSYCHIC_SEED("Psychic Seed"),
    MISTY_SEED("Misty Seed"),
    GRASSY_SEED("Grassy Seed"),
    FIGHTING_MEMORY("Fighting Memory", ItemCategory.SILVALLY_MEMORY),
    FLYING_MEMORY("Flying Memory", ItemCategory.SILVALLY_MEMORY),
    POISON_MEMORY("Poison Memory", ItemCategory.SILVALLY_MEMORY),
    GROUND_MEMORY("Ground Memory", ItemCategory.SILVALLY_MEMORY),
    ROCK_MEMORY("Rock Memory", ItemCategory.SILVALLY_MEMORY),
    BUG_MEMORY("Bug Memory", ItemCategory.SILVALLY_MEMORY),
    GHOST_MEMORY("Ghost Memory", ItemCategory.SILVALLY_MEMORY),
    STEEL_MEMORY("Steel Memory", ItemCategory.SILVALLY_MEMORY),
    FIRE_MEMORY("Fire Memory", ItemCategory.SILVALLY_MEMORY),
    WATER_MEMORY("Water Memory", ItemCategory.SILVALLY_MEMORY),
    GRASS_MEMORY("Grass Memory", ItemCategory.SILVALLY_MEMORY),
    ELECTRIC_MEMORY("Electric Memory", ItemCategory.SILVALLY_MEMORY),
    PSYCHIC_MEMORY("Psychic Memory", ItemCategory.SILVALLY_MEMORY),
    ICE_MEMORY("Ice Memory", ItemCategory.SILVALLY_MEMORY),
    DRAGON_MEMORY("Dragon Memory", ItemCategory.SILVALLY_MEMORY),
    DARK_MEMORY("Dark Memory", ItemCategory.SILVALLY_MEMORY),
    FAIRY_MEMORY("Fairy Memory", ItemCategory.SILVALLY_MEMORY);

    private final String NAME;
    private final ItemCategory CATEGORY;

    Item(@Nonnull String name, @Nonnull ItemCategory category) {
        Holder.MAP.put(name.toLowerCase(), this);
        this.NAME = name;
        this.CATEGORY = category;
    }

    Item(@Nonnull String name) {
        this(name, ItemCategory.DEFAULT);
    }

    public static Item getItem(@Nonnull String item) {
        item = item.toLowerCase();
        if (!Holder.MAP.containsKey(item)) {
            throw new NullPointerException("Item " + item + " doesn't exist");
        }

        return Holder.MAP.get(item);
    }

    public static boolean isArceusPlate(@Nonnull Item item) {
        switch (item) {
            case DRACO_PLATE:
            case DREAD_PLATE:
            case EARTH_PLATE:
            case FIST_PLATE:
            case FLAME_PLATE:
            case ICICLE_PLATE:
            case INSECT_PLATE:
            case IRON_PLATE:
            case MEADOW_PLATE:
            case MIND_PLATE:
            case PIXIE_PLATE:
            case SKY_PLATE:
            case SPLASH_PLATE:
            case SPOOKY_PLATE:
            case STONE_PLATE:
            case TOXIC_PLATE:
            case ZAP_PLATE:
                return true;
            default:
                return false;
        }
    }
    
    public static boolean isGenesectDrive(@Nonnull Item item) {
        switch (item) {
            case BURN_DRIVE:
            case CHILL_DRIVE:
            case DOUSE_DRIVE:
            case SHOCK_DRIVE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isMegaStone(@Nonnull Item item) {
        switch (item) {
            case ABOMASITE:
            case ABSOLITE:
            case AERODACTYLITE:
            case AGGRONITE:
            case ALAKAZITE:
            case ALTARIANITE:
            case AMPHAROSITE:
            case AUDINITE:
            case BANETTITE:
            case BEEDRILLITE:
            case BLASTOISINITE:
            case BLAZIKENITE:
            case CAMERUPTITE:
            case CHARIZARDITE_X:
            case CHARIZARDITE_Y:
            case DIANCITE:
            case GALLADITE:
            case GARCHOMPITE:
            case GARDEVOIRITE:
            case GENGARITE:
            case GLALITITE:
            case GYARADOSITE:
            case HERACRONITE:
            case HOUNDOOMINITE:
            case KANGASKHANITE:
            case LATIASITE:
            case LATIOSITE:
            case LOPUNNITE:
            case LUCARIONITE:
            case MANECTITE:
            case MAWILITE:
            case MEDICHAMITE:
            case METAGROSSITE:
            case MEWTWONITE_X:
            case MEWTWONITE_Y:
            case PIDGEOTITE:
            case PINSIRITE:
            case SABLENITE:
            case SALAMENCITE:
            case SCEPTILITE:
            case SCIZORITE:
            case SHARPEDONITE:
            case SLOWBRONITE:
            case STEELIXITE:
            case SWAMPERTITE:
            case TYRANITARITE:
            case VENUSAURITE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isOrb(@Nonnull Item item) {
        switch (item) {
            case RED_ORB:
            case BLUE_ORB:
                return true;
            default:
                return false;
        }
    }

    public static boolean isZCrystal(@Nonnull Item item) {
        switch (item) {
            case BUGINIUM_Z:
            case DARKINIUM_Z:
            case DRAGONIUM_Z:
            case ELECTRIUM_Z:
            case FAIRIUM_Z:
            case FIGHTINIUM_Z:
            case FIRIUM_Z:
            case FLYINIUM_Z:
            case GHOSTIUM_Z:
            case GRASSIUM_Z:
            case GROUNDIUM_Z:
            case ICIUM_Z:
            case NORMALIUM_Z:
            case POISONIUM_Z:
            case PSYCHIUM_Z:
            case ROCKIUM_Z:
            case STEELIUM_Z:
            case WATERIUM_Z:
            case ALORAICHIUM_Z:
            case DECIDIUM_Z:
            case EEVIUM_Z:
            case INCINIUM_Z:
            case MARSHADIUM_Z:
            case MEWNIUM_Z:
            case PIKANIUM_Z:
            case PIKASHUNIUM_Z:
            case PRIMARIUM_Z:
            case SNORLIUM_Z:
            case TAPUNIUM_Z:
                return true;
            default:
                return false;
        }
    }

    public static boolean isSilvallyMemory(@Nonnull Item item) {
        switch (item) {
            case BUG_MEMORY:
            case DARK_MEMORY:
            case DRAGON_MEMORY:
            case ELECTRIC_MEMORY:
            case FAIRY_MEMORY:
            case FIGHTING_MEMORY:
            case FIRE_MEMORY:
            case FLYING_MEMORY:
            case GHOST_MEMORY:
            case GRASS_MEMORY:
            case GROUND_MEMORY:
            case ICE_MEMORY:
            case POISON_MEMORY:
            case PSYCHIC_MEMORY:
            case ROCK_MEMORY:
            case STEEL_MEMORY:
            case WATER_MEMORY:
                return true;
            default:
                return false;
        }
    }

    public String getName() {
        return this.NAME;
    }

    public ItemCategory getCategory() {
        return this.CATEGORY;
    }

    protected void use(@Nonnull Pokemon user, @Nonnull Battle battle) {}

    private static class Holder {
        static Map<String, Item> MAP = new HashMap<>();
    }

}
