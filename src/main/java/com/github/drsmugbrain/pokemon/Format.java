package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 15/07/2017.
 */
public enum Format {

    ONE_VERSUS_ONE("1v1"),
    TWO_VERSUS_TWO_DOUBLES("2v2 Doubles"),
    ALMOST_ANY_ABILITY("Almost Any Ability"),
    ANYTHING_GOES("Anything Goes", "AG"),
    AVERAGEMONS("Averagemons"),
    BALANCED_HACKMONS("Balanced Hackmons", "BH"),
    BATTLE_SPOT_DOUBLES("Battle Spot Doubles"),
    BATTLE_SPOT_SINGLES("Battle Spot Singles"),
    BATTLE_SPOT_TRIPLES("Battle Spot Triples"),
    BORDERLINE("Borderline"),
    BORDERLINE_2("Borderline 2"),
    BORDERLINE_3("Borderline 3"),
    BORDERLINE_4("Borderline 4"),
    CHALLENGE_CUP("Challenge Cup", "CC"),
    CHALLENGE_CUP_ONE_VERSUS_ONE("Challenge Cup 1v1", "CC1v1"),
    CREATE_A_POKEMON("Create-a-Pokemon", "CC"),
    DOUBLES("Doubles"),
    DOUBLES_UBERS("Doubles Ubers"),
    DOUBLES_UNDERUSED("Doubles UnderUsed", "Doubles UU"),
    HACKMONS_CUP("Hackmons Cup"),
    HIDDEN_TYPE("Hidden Type"),
    INVERSE_BATTLE("Inverse Battle"),
    LIMBO("Limbo"),
    LITTLE_CUP("Little Cup", "LC"),
    MIDDLE_CUP("Middle Cup"),
    MIX_AND_MEGA("Mix and Mega"),
    MONOTYPE("Monotype"),
    MONOTYPE_RANDOM_BATTLE("Monotype Random Battle"),
    NEVERUSED("NeverUsed", "NU"),
    OVERUSED("OverUsed", "OU"),
    PARTIALLYUSED("PartiallyUsed", "PU"),
    RANDOM_BATTLE("Random Battle"),
    RANDOM_DOUBLES("Random Doubles"),
    RANDOM_TRIPLES("Random Triples"),
    RARELYUSED("RarelyUsed", "RU"),
    STABMONS("STABmons"),
    SEASONAL("Seasonal"),
    SKETCHMONS("Sketchmons"),
    SMOGON_TRIPLES("Smogon Triples"),
    TIER_SHIFT("Tier Shift"),
    UBER("Uber"),
    UNDERUSED("UnderUsed", "UU"),
    VGC11("VGC11"),
    VGC12("VGC12"),
    VGC13("VGC13"),
    VGC14("VGC14"),
    VGC15("VGC15"),
    VGC16("VGC16"),
    VGC17("VGC17");

    private final String NAME;
    private final String ABBREVIATION;

    Format(@Nonnull String name, @Nullable String abbreviation) {
        this.NAME = name;
        this.ABBREVIATION = abbreviation;
    }

    Format(@Nonnull String name) {
        this(name, null);
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nullable
    public String getAbbreviation() {
        return this.ABBREVIATION;
    }

    protected boolean isValid(Battle battle) {
        return true;
    }

}
