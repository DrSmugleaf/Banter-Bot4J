package com.github.drsmugleaf.pokemon.pokemon;

import com.github.drsmugleaf.pokemon.ability.Abilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 24/06/2017.
 */
public enum Gender { // TODO: Add list of Genderless Pokemon / Pokemon gender ratios

    MALE("Male", "(M)"),
    FEMALE("Female", "(F)"),
    GENDERLESS("Genderless", null);

    @NotNull
    public final String NAME;

    @Nullable
    public final String ABBREVIATION;

    Gender(@NotNull String name, @Nullable String abbreviation) {
        Holder.MAP.put(name.toLowerCase(), this);
        NAME = name;
        ABBREVIATION = abbreviation;
    }

    @NotNull
    public static Gender getGender(@NotNull String gender) {
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

    @NotNull
    public static Gender getRandomGender(@NotNull Species pokemon) {
        List<Gender> genders = pokemon.getValidGenders();
        int index = ThreadLocalRandom.current().nextInt(genders.size());
        return genders.get(index);
    }

    public static boolean isOppositeGender(@NotNull Pokemon pokemon1, @NotNull Pokemon pokemon2) {
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

    public static boolean isInfatuatable(@NotNull Pokemon attacker, @NotNull Pokemon defender) {
        if (defender.ABILITY.get() == Abilities.OBLIVIOUS) {
            return false;
        }

        return Gender.isOppositeGender(attacker, defender);
    }

    @NotNull
    public String getName() {
        return NAME;
    }

    @Nullable
    public String getAbbreviation() {
        return ABBREVIATION;
    }

    private static class Holder {
        @NotNull
        static Map<String, Gender> MAP = new HashMap<>();
    }

}
