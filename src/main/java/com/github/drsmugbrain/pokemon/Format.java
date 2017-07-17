package com.github.drsmugbrain.pokemon;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    VGC14("VGC14"),
    VGC15("VGC15"),
    VGC16("VGC16"),
    VGC17("VGC17");

    static {
        ONE_VERSUS_ONE
                .setClauses(
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.SPECIES_CLAUSE
                );

        ALMOST_ANY_ABILITY
                .setTier(Tier.OVERUSED)
                .setClauses(Clause.ABILITY_CLAUSE);

        ANYTHING_GOES
                .setClauses(Clause.ENDLESS_BATTLE_CLAUSE);

        BALANCED_HACKMONS
                .setClauses(
                        Clause.CFZ_CLAUSE,
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.ABILITY_CLAUSE
                );

        BATTLE_SPOT_SINGLES
                .setClauses(
                        Clause.SPECIES_CLAUSE,
                        Clause.ITEM_CLAUSE,
                        Clause.BATTLE_TIMER,
                        Clause.LEVEL_RESTRICTIONS,
                        Clause.THE_3DS_BORN_RULE
                );

        DOUBLES
                .setClauses(
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.SPECIES_CLAUSE
                );

        LITTLE_CUP
                .setTier(Tier.LITTLE_CUP)
                .setClauses(
                        Clause.SPECIES_CLAUSE,
                        Clause.SLEEP_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.SWAGGER_CLAUSE,
                        Clause.LEVEL_5_CLAUSE
                );

        MIX_AND_MEGA
                .setTier(Tier.UBERS)
                .setClauses(Clause.MEGA_STONE_AND_ORB_CLAUSE);

        MONOTYPE
                .setTier(Tier.OVERUSED)
                .setClauses(
                        Clause.SAME_TYPE_CLAUSE,
                        Clause.SWAGGER_CLAUSE
                );

        NEVERUSED
                .setTier(Tier.NEVERUSED);

        OVERUSED
                .setTier(Tier.OVERUSED);

        PARTIALLYUSED
                .setTier(Tier.PARTIALLYUSED);

        RARELYUSED
                .setTier(Tier.RARELYUSED)
                .setClauses(
                        Clause.SPECIES_CLAUSE,
                        Clause.SLEEP_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.SWAGGER_CLAUSE
                );

        STABMONS
                .setTier(Tier.OVERUSED);

        SKETCHMONS
                .setTier(Tier.OVERUSED)
                .setClauses(Clause.SKETCH_CLAUSE);

        UBER
                .setTier(Tier.UBERS);

        UNDERUSED
                .setTier(Tier.UNDERUSED)
                .setClauses(
                        Clause.SPECIES_CLAUSE,
                        Clause.SLEEP_CLAUSE,
                        Clause.EVASION_CLAUSE,
                        Clause.OHKO_CLAUSE,
                        Clause.MOODY_CLAUSE,
                        Clause.ENDLESS_BATTLE_CLAUSE,
                        Clause.SWAGGER_CLAUSE
                );

        VGC17
                .setClauses(
                        Clause.ITEM_CLAUSE,
                        Clause.SPECIES_CLAUSE,
                        Clause.LEVEL_LIMIT,
                        Clause.TIME_LIMIT
                );
    }

    private final String NAME;
    private final String ABBREVIATION;
    private final List<Clause> CLAUSES = new ArrayList<>();
    private Tier TIER;

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

    public boolean isValid(Battle battle) {
        if (!TIER.isValid(battle)) {
            return false;
        }

        for (Clause clause : this.CLAUSES) {
            if (!clause.isValid(battle)) {
                return false;
            }
        }

        return true;
    }

    @Nonnull
    public List<Clause> getClauses() {
        return this.CLAUSES;
    }

    private Format addClauses(Clause... clauses) {
        Collections.addAll(this.CLAUSES, clauses);
        return this;
    }

    private Format setClauses(Clause... clauses) {
        this.CLAUSES.clear();
        return this.addClauses(clauses);
    }

    @Nonnull
    public Tier getTier() {
        return this.TIER;
    }

    public Format setTier(Tier tier) {
        this.TIER = tier;
        return this;
    }

}
