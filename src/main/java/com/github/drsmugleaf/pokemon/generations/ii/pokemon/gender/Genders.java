package com.github.drsmugleaf.pokemon.generations.ii.pokemon.gender;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum Genders implements IGender {

    MALE("Male", "(M)", "Female"),
    FEMALE("Female", "(F)", "Male"),
    GENDERLESS("Genderless", "", "");

    private final String ABBREVIATION;
    private final String NAME;
    private final String OPPOSITE;

    Genders(String name, String abbreviation, String opposite) {
        NAME = name;
        ABBREVIATION = abbreviation;
        OPPOSITE = opposite;
    }

    @Override
    public String getAbbreviation() {
        return ABBREVIATION;
    }

    @Override
    public boolean isOpposite(IGender other) {
        return OPPOSITE.equals(other.getName());
    }

    @Override
    public String getName() {
        return NAME;
    }

}
