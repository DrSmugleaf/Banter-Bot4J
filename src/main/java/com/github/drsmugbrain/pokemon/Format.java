package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by DrSmugleaf on 15/07/2017.
 */
public enum Format {

    ONE_VERSUS_ONE("1v1", Clause.ENDLESS_BATTLE_CLAUSE, Clause.EVASION_CLAUSE, Clause.MOODY_CLAUSE, Clause.OHKO_CLAUSE, Clause.SPECIES_CLAUSE),
    TWO_VERSUS_TWO_DOUBLES("2v2 Doubles"),
    ALMOST_ANY_ABILITY("Almost Any Ability", Clause.ABILITY_CLAUSE),
    ANYTHING_GOES("Anything Goes", "AG", Clause.ENDLESS_BATTLE_CLAUSE),
    AVERAGEMONS("Averagemons"),
    BALANCED_HACKMONS("Balanced Hackmons", "BH", Clause.CFZ_CLAUSE, Clause.ENDLESS_BATTLE_CLAUSE, Clause.OHKO_CLAUSE, Clause.EVASION_CLAUSE, Clause.ABILITY_CLAUSE),
    BATTLE_SPOT_DOUBLES("Battle Spot Doubles"),
    BATTLE_SPOT_SINGLES("Battle Spot Singles", Clause.SPECIES_CLAUSE, Clause.ITEM_CLAUSE, Clause.BATTLE_TIMER, Clause.LEVEL_RESTRICTIONS, Clause.THE_3DS_BORN_RULE),
    BATTLE_SPOT_TRIPLES("Battle Spot Triples"),
    BORDERLINE("Borderline"),
    BORDERLINE_2("Borderline 2"),
    BORDERLINE_3("Borderline 3"),
    BORDERLINE_4("Borderline 4"),
    CHALLENGE_CUP("Challenge Cup", "CC"),
    CHALLENGE_CUP_ONE_VERSUS_ONE("Challenge Cup 1v1", "CC1v1"),
    CREATE_A_POKEMON("Create-a-Pokemon", "CC"),
    DOUBLES("Doubles", Clause.ENDLESS_BATTLE_CLAUSE, Clause.EVASION_CLAUSE, Clause.MOODY_CLAUSE, Clause.OHKO_CLAUSE, Clause.SPECIES_CLAUSE),
    DOUBLES_UBERS("Doubles Ubers"),
    DOUBLES_UNDERUSED("Doubles UnderUsed", "Doubles UU"),
    HACKMONS_CUP("Hackmons Cup"),
    HIDDEN_TYPE("Hidden Type"),
    INVERSE_BATTLE("Inverse Battle"),
    LIMBO("Limbo"),
    LITTLE_CUP("Little Cup", "LC", Clause.SPECIES_CLAUSE, Clause.SLEEP_CLAUSE, Clause.EVASION_CLAUSE, Clause.OHKO_CLAUSE, Clause.MOODY_CLAUSE, Clause.ENDLESS_BATTLE_CLAUSE, Clause.SWAGGER_CLAUSE, Clause.LEVEL_5_CLAUSE),
    MIDDLE_CUP("Middle Cup"),
    MIX_AND_MEGA("Mix and Mega", Clause.ENDLESS_BATTLE_CLAUSE, Clause.MOODY_CLAUSE, Clause.OHKO_CLAUSE, Clause.SLEEP_CLAUSE, Clause.SPECIES_CLAUSE, Clause.MEGA_STONE_AND_ORB_CLAUSE),
    MONOTYPE("Monotype", Clause.ENDLESS_BATTLE_CLAUSE, Clause.BATON_PASS_CLAUSE, Clause.EVASION_CLAUSE, Clause.MOODY_CLAUSE, Clause.OHKO_CLAUSE, Clause.SLEEP_CLAUSE, Clause.SPECIES_CLAUSE, Clause.SAME_TYPE_CLAUSE, Clause.SWAGGER_CLAUSE),
    MONOTYPE_RANDOM_BATTLE("Monotype Random Battle"),
    NEVERUSED("NeverUsed", "NU"),
    OVERUSED("OverUsed", "OU", Clause.ENDLESS_BATTLE_CLAUSE, Clause.BATON_PASS_CLAUSE, Clause.EVASION_CLAUSE, Clause.MOODY_CLAUSE, Clause.OHKO_CLAUSE, Clause.SLEEP_CLAUSE, Clause.SPECIES_CLAUSE),
    PARTIALLYUSED("PartiallyUsed", "PU"),
    RANDOM_BATTLE("Random Battle"),
    RANDOM_DOUBLES("Random Doubles"),
    RANDOM_TRIPLES("Random Triples"),
    RARELYUSED("RarelyUsed", "RU", Clause.ENDLESS_BATTLE_CLAUSE, Clause.EVASION_CLAUSE, Clause.MOODY_CLAUSE, Clause.OHKO_CLAUSE, Clause.SLEEP_CLAUSE, Clause.SPECIES_CLAUSE),
    STABMONS("STABmons", Clause.ENDLESS_BATTLE_CLAUSE, Clause.BATON_PASS_CLAUSE, Clause.EVASION_CLAUSE, Clause.MOODY_CLAUSE, Clause.OHKO_CLAUSE, Clause.SLEEP_CLAUSE, Clause.SPECIES_CLAUSE),
    SEASONAL("Seasonal"),
    SKETCHMONS("Sketchmons", Clause.ENDLESS_BATTLE_CLAUSE, Clause.BATON_PASS_CLAUSE, Clause.EVASION_CLAUSE, Clause.MOODY_CLAUSE, Clause.OHKO_CLAUSE, Clause.SLEEP_CLAUSE, Clause.SPECIES_CLAUSE),
    SMOGON_TRIPLES("Smogon Triples"),
    TIER_SHIFT("Tier Shift"),
    UBER("Uber"),
    UNDERUSED("UnderUsed", "UU", Clause.SPECIES_CLAUSE, Clause.SLEEP_CLAUSE, Clause.EVASION_CLAUSE, Clause.OHKO_CLAUSE, Clause.MOODY_CLAUSE, Clause.ENDLESS_BATTLE_CLAUSE, Clause.SWAGGER_CLAUSE),
    VGC11("VGC11"),
    VGC12("VGC12"),
    VGC13("VGC13"),
    VGC14("VGC14"),
    VGC15("VGC15"),
    VGC16("VGC16"),
    VGC17("VGC17", Clause.ITEM_CLAUSE, Clause.SPECIES_CLAUSE, Clause.LEVEL_LIMIT, Clause.TIME_LIMIT);

    private final String NAME;
    private final String ABBREVIATION;
    private final Clause[] CLAUSES;

    Format(@Nonnull String name, @Nullable String abbreviation, @Nonnull Clause... clauses) {
        this.NAME = name;
        this.ABBREVIATION = abbreviation;
        this.CLAUSES = clauses;
    }

    Format(@Nonnull String name, @Nullable String abbreviation) {
        this(name, abbreviation, new Clause[]{});
    }

    Format(@Nonnull String name, @Nonnull Clause... clauses) {
        this(name, null, clauses);
    }

    Format(@Nonnull String name) {
        this(name, null, new Clause[]{});
    }

    @Nonnull
    public String getName() {
        return this.NAME;
    }

    @Nullable
    public String getAbbreviation() {
        return this.ABBREVIATION;
    }

    @Nonnull
    public Clause[] getClauses() {
        return this.CLAUSES;
    }

    public boolean isValid(Battle battle) {
        for (Clause clause : this.CLAUSES) {
            if (!clause.isValid(battle)) {
                return false;
            }
        }

        return true;
    }

}
