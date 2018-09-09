package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.pokemon.ability.Abilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * Created by DrSmugleaf on 24/06/2017.
 */
public enum Gender { // TODO: Add list of Genderless Pokemon / Pokemon gender ratios

    MALE("Male", "(M)"),
    FEMALE("Female", "(F)"),
    GENDERLESS("Genderless", null);

    @Nonnull
    public final String NAME;

    @Nullable
    public final String ABBREVIATION;

    Gender(@Nonnull String name, @Nullable String abbreviation) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;
        ABBREVIATION = abbreviation;
    }

    @Nonnull
    public static Gender getGender(@Nonnull String gender) {
        gender = gender.toLowerCase();

        if (Objects.equals(gender, Gender.MALE.ABBREVIATION)) {
            return Gender.MALE;
        } else if (Objects.equals(gender, Gender.FEMALE.ABBREVIATION)) {
            return Gender.FEMALE;
        }

        if (!Holder.MAP.containsKey(gender)) {
            throw new NullPointerException("Gender " + gender + " doesn't exist");
        }

        return Holder.MAP.get(gender);
    }

    @Nonnull
    public static Gender getRandomGender() {
        if (new Random().nextBoolean()) {
            return Gender.MALE;
        } else {
            return Gender.FEMALE;
        }
    }

    public static boolean isOppositeGender(@Nonnull Pokemon pokemon1, @Nonnull Pokemon pokemon2) {
        switch (pokemon1.getGender()) {
            case MALE:
                return pokemon2.getGender() == Gender.FEMALE;
            case FEMALE:
                return pokemon2.getGender() == Gender.MALE;
            case GENDERLESS:
                return false;
            default:
                throw new IllegalArgumentException("Invalid Gender for pokemon 1: " + pokemon1.getGender());
        }
    }

    public static boolean isInfatuatable(@Nonnull Pokemon attacker, @Nonnull Pokemon defender) {
        if (defender.ABILITY.get() == Abilities.OBLIVIOUS) {
            return false;
        }

        return Gender.isOppositeGender(attacker, defender);
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

    @Nullable
    public String getAbbreviation() {
        return ABBREVIATION;
    }

    private static class Holder {
        @Nonnull
        static Map<String, Gender> MAP = new HashMap<>();
    }

}
