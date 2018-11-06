package com.github.drsmugleaf.deadbydaylight;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public enum Killers {

    CANNIBAL("Cannibal", "Junior Sawyer"),
    CLOWN("Clown", "Jeffrey Hawk"),
    DOCTOR("Doctor", "Herman Carter"),
    HAG("Hag", "Lisa Sherwood"),
    HILLBILLY("Hillbilly", "Max Thompson Jr."),
    HUNTRESS("Huntress", "Anna"),
    NIGHTMARE("Nightmare", "Freddy Krueger"),
    NURSE("Nurse", "Sally Smithson"),
    PIG("Pig", "Amanda Young"),
    SHAPE("Shape", "Michael Myers"),
    SPIRIT("Spirit", "Rin Yamaoka"),
    TRAPPER("Trapper", "Evan MacMillan"),
    WRAITH("Wraith", "Philip Ojomo");

    @Nonnull
    public final String NICKNAME;

    @Nonnull
    public final String NAME;

    Killers(@Nonnull String nickname, @Nonnull String name) {
        NICKNAME = nickname;
        NAME = name;
    }

    @Nonnull
    public String getNickname() {
        return NICKNAME;
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

}
