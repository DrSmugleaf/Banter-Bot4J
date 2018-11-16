package com.github.drsmugleaf.deadbydaylight;

import com.github.drsmugleaf.BanterBot4J;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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

    @NotNull
    private static final String IMAGES_PATH = Objects.requireNonNull(Killers.class.getClassLoader().getResource("deadbydaylight/killers")).getFile();

    @NotNull
    public final String FULL_NAME;

    @NotNull
    public final String NAME;

    Killers(@NotNull String fullName, @NotNull String name) {
        NAME = name;
        FULL_NAME = fullName;
    }

    private static boolean nameMatches(@NotNull Killers killer, @Nullable String name) {
        if (name == null) {
            return false;
        }

        return killer.FULL_NAME.equalsIgnoreCase(name) ||
               killer.NAME.equalsIgnoreCase(name) ||
               ("The " + killer.FULL_NAME).equalsIgnoreCase(name) ||
               ("The " + killer.NAME).equalsIgnoreCase(name);
    }

    @Nullable
    public static Killers from(@Nullable String name) {
        for (Killers killer : values()) {
            if (nameMatches(killer, name)) {
                return killer;
            }
        }

        return null;
    }

    @NotNull
    public static Killers random() {
        Killers[] killers = values();
        int index = ThreadLocalRandom.current().nextInt(1, killers.length);
        return killers[index];
    }

    @NotNull
    public String getFullName() {
        return FULL_NAME;
    }

    @NotNull
    @Override
    public InputStream getImage() {
        String fileName = "/" + NAME.toLowerCase() + ".png";
        try {
            return new FileInputStream(IMAGES_PATH + fileName);
        } catch (FileNotFoundException e) {
            BanterBot4J.warn("Image for DBD killer " + NAME + " not found", e);
            throw new IllegalStateException("Image for " + NAME + " not found", e);
        }
    }

    @NotNull
    @Override
    public String getName() {
        return NAME;
    }

}
