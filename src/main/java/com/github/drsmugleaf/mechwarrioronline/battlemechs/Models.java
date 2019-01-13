package com.github.drsmugleaf.mechwarrioronline.battlemechs;

import org.jetbrains.annotations.NotNull;

/**
 * Created by DrSmugleaf on 10/01/2019
 */
public enum Models {

    ADDER("Adder", Tonnage.LIGHT),
    ARCTIC_CHEETAH("Arctic Cheetah", Tonnage.LIGHT),
    COMMANDO("Commando", Tonnage.LIGHT),
    COUGAR("Cougar", Tonnage.LIGHT),
    FIRESTARTER("Firestarter", Tonnage.LIGHT),
    FLEA("Flea", Tonnage.LIGHT),
    INCUBUS("Incubus", Tonnage.LIGHT),
    JAVELIN("Javelin", Tonnage.LIGHT),
    JENNER("Jenner", Tonnage.LIGHT),
    JENNER_IIC("Jenner IIC", Tonnage.LIGHT),
    KIT_FOX("Kit Fox", Tonnage.LIGHT),
    LOCUST("Locust", Tonnage.LIGHT),
    MIST_LYNX("Mist Lynx", Tonnage.LIGHT),
    OSIRIS("Osiris", Tonnage.LIGHT),
    PANTHER("Panther", Tonnage.LIGHT),
    PIRANHA("Piranha", Tonnage.LIGHT),
    RAVEN("Raven", Tonnage.LIGHT),
    SPIDER("Spider", Tonnage.LIGHT),
    URBANMECH("UrbanMech", Tonnage.LIGHT),
    WOLFHOUND("Wolfhound", Tonnage.LIGHT),

    ARCTIC_WOLF("Arctic Wolf", Tonnage.MEDIUM),
    ASSASSIN("Assassin", Tonnage.MEDIUM),
    BLACKJACK("Blackjack", Tonnage.MEDIUM),
    BLACK_LANNER("Black Lanner", Tonnage.MEDIUM),
    BUSHWACKER("Bushwacker", Tonnage.MEDIUM),
    CENTURION("Centurion", Tonnage.MEDIUM),
    CICADA("Cicada", Tonnage.MEDIUM),
    CRAB("Crab", Tonnage.MEDIUM),
    ENFORCER("Enforcer", Tonnage.MEDIUM),
    GRIFFIN("Griffin", Tonnage.MEDIUM),
    HELLSPAWN("Hellspawn", Tonnage.MEDIUM),
    HUNCHBACK("Hunchback", Tonnage.MEDIUM),
    HUNCHBACK_IIC("Hunchback IIC", Tonnage.MEDIUM),
    HUNTSMAN("Huntsman", Tonnage.MEDIUM),
    ICE_FERRET("Ice Ferret", Tonnage.MEDIUM),
    NOVA("Nova", Tonnage.MEDIUM),
    KINTARO("Kintaro", Tonnage.MEDIUM),
    PHOENIX_HAWK("Phoenix Hawk", Tonnage.MEDIUM),
    SHADOW_CAT("Shadow Cat", Tonnage.MEDIUM),
    SHADOWHAWK("Shadow Hawk", Tonnage.MEDIUM),
    STORMCROW("Stormcrow", Tonnage.MEDIUM),
    TREBUCHET("Trebuchet", Tonnage.MEDIUM),
    WOLVERINE("Wolverine", Tonnage.MEDIUM),
    UZIEL("Uziel", Tonnage.MEDIUM),
    VAPOR_EAGLE("Vapor Eagle", Tonnage.MEDIUM),
    VINDICATOR("Vindicator", Tonnage.MEDIUM),
    VIPER("Viper", Tonnage.MEDIUM),
    VULCAN("Vulcan", Tonnage.MEDIUM),

    ARCHER("Archer", Tonnage.HEAVY),
    BLACK_KNIGHT("Black Knight", Tonnage.HEAVY),
    CHAMPION("Champion", Tonnage.HEAVY),
    CATAPHRACT("Cataphract", Tonnage.HEAVY),
    CATAPULT("Catapult", Tonnage.HEAVY),
    DRAGON("Dragon", Tonnage.HEAVY),
    EBON_JAGUAR("Ebon Jaguar", Tonnage.HEAVY),
    GRASSHOPPER("Grasshopper", Tonnage.HEAVY),
    HELLBRINGER("Hellbringer", Tonnage.HEAVY),
    HELLFIRE("Hellfire", Tonnage.HEAVY),
    JAGERMECH("JagerMech", Tonnage.HEAVY),
    LINEBACKER("Linebacker", Tonnage.HEAVY),
    MAD_DOG("Mad Dog", Tonnage.HEAVY),
    MARAUDER("Marauder", Tonnage.HEAVY),
    NIGHT_GYR("Night Gyr", Tonnage.HEAVY),
    NOVA_CAT("Nova Cat", Tonnage.HEAVY),
    ORION("Orion", Tonnage.HEAVY),
    ORION_IIC("Orion IIC", Tonnage.HEAVY),
    QUICKDRAW("Quickdraw", Tonnage.HEAVY),
    RIFLEMAN("Rifleman", Tonnage.HEAVY),
    ROUGHNECK("Roughneck", Tonnage.HEAVY),
    SUMMONER("Summoner", Tonnage.HEAVY),
    SUN_SPIDER("Sun Spider", Tonnage.HEAVY),
    THANATOS("Thanatos", Tonnage.HEAVY),
    THUNDERBOLT("Thunderbolt", Tonnage.HEAVY),
    TIMBER_WOLF("Timber Wolf", Tonnage.HEAVY),
    WARHAMMER("Warhammer", Tonnage.HEAVY),

    ANNIHILATOR("Annihilator", Tonnage.ASSAULT),
    ATLAS("Atlas", Tonnage.ASSAULT),
    AWESOME("Awesome", Tonnage.ASSAULT),
    BANSHEE("Banshee", Tonnage.ASSAULT),
    BATTLEMASTER("Battlemaster", Tonnage.ASSAULT),
    BLOOD_ASP("Blood Asp", Tonnage.ASSAULT),
    CHARGER("Charger", Tonnage.ASSAULT),
    CYCLOPS("Cyclops", Tonnage.ASSAULT),
    DIRE_WOLF("Dire Wolf", Tonnage.ASSAULT),
    EXECUTIONER("Executioner", Tonnage.ASSAULT),
    FAFNIR("Fafnir", Tonnage.ASSAULT),
    GARGOYLE("Gargoyle", Tonnage.ASSAULT),
    HATAMOTO_CHI("Hatamoto-Chi", Tonnage.ASSAULT),
    HIGHLANDER("Highlander", Tonnage.ASSAULT),
    HIGHLANDER_IIC("Highlander IIC", Tonnage.ASSAULT),
    KING_CRAB("King Crab", Tonnage.ASSAULT),
    KODIAK("Kodiak", Tonnage.ASSAULT),
    MAD_CAT_MK_II("Mad Cat Mk II", Tonnage.ASSAULT),
    MARAUDER_II("Marauder II", Tonnage.ASSAULT),
    MARAUDER_IIC("Marauder IIC", Tonnage.ASSAULT),
    MAULER("Mauler", Tonnage.ASSAULT),
    NIGHTSTAR("Nightstar", Tonnage.ASSAULT),
    STALKER("Stalker", Tonnage.ASSAULT),
    SUPERNOVA("Supernova", Tonnage.ASSAULT),
    VICTOR("Victor", Tonnage.ASSAULT),
    WARHAWK("Warhawk", Tonnage.ASSAULT),
    ZEUS("Zeus", Tonnage.ASSAULT);

    @NotNull
    private final String NAME;

    @NotNull
    private final Tonnage TONNAGE;

    Models(@NotNull String name, @NotNull Tonnage tonnage) {
        NAME = name;
        TONNAGE = tonnage;
    }

    public static Models from(@NotNull String name) {
        name = name.toLowerCase();

        for (Models model : values()) {
            if (model.NAME.toLowerCase().equals(name)) {
                return model;
            }
        }

        throw new IllegalArgumentException("No model found with name " + name);
    }

    @NotNull
    public String getName() {
        return NAME;
    }

    @NotNull
    public Tonnage getTonnage() {
        return TONNAGE;
    }

}
