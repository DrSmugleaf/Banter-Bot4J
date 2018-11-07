package com.github.drsmugleaf.deadbydaylight;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public enum Killers implements ICharacter {

    ALL("All", "All"),
    CANNIBAL("Junior Sawyer", "Cannibal"),
    CLOWN("Jeffrey Hawk", "Clown"),
    DOCTOR("Herman Carter", "Doctor"),
    HAG("Lisa Sherwood", "Hag"),
    HILLBILLY("Max Thompson Jr.", "Hillbilly"),
    HUNTRESS("Anna", "Huntress"),
    NIGHTMARE("Freddy Krueger", "Nightmare"),
    NURSE("Sally Smithson", "Nurse"),
    PIG("Amanda Young", "Pig"),
    SHAPE("Michael Myers", "Shape"),
    SPIRIT("Rin Yamaoka", "Spirit"),
    TRAPPER("Evan MacMillan", "Trapper"),
    WRAITH("Philip Ojomo", "Wraith");

    @Nonnull
    public final String FULL_NAME;

    @Nonnull
    public final String NAME;

    Killers(@Nonnull String fullName, @Nonnull String name) {
        NAME = name;
        FULL_NAME = fullName;
    }

    @Nonnull
    public String getFullName() {
        return FULL_NAME;
    }

    @Nonnull
    @Override
    public String getName() {
        return NAME;
    }

}
