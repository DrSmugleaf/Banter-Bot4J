package com.github.drsmugleaf.deadbydaylight;

import com.github.drsmugleaf.BanterBot4J;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.*;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public enum Survivors implements ICharacter {

    ALL("All"),
    ACE_VISCONTI("Ace Visconti"),
    ADAM_FRANCIS("Adam Francis"),
    BILL_OVERBECK("Bill Overbeck"),
    CLAUDETTE_MOREL("Claudette Morel"),
    DAVID_KING("David King"),
    DAVID_TAPP("David Tapp"),
    DWIGHT_FAIRFIELD("Dwight Fairfield"),
    FENG_MIN("Feng Min"),
    JAKE_PARK("Jake Park"),
    KATE_DENSON("Kate Denson"),
    LAURIE_STRODE("Laurie Strode"),
    MEG_THOMAS("Meg Thomas"),
    NEA_KARLSSON("Nea Karlsson"),
    QUENTIN_SMITH("Quentin Smith");

    @NotNull
    private static final String IMAGES_PATH = Objects.requireNonNull(Survivors.class.getClassLoader().getResource("deadbydaylight/survivors")).getFile();

    @NotNull
    public final String NAME;

    Survivors(@NotNull String name) {
        NAME = name;
    }

    @Nullable
    public static Survivors from(@NotNull String name) {
        for (Survivors survivor : values()) {
            if (survivor.NAME.equalsIgnoreCase(name)) {
                return survivor;
            }

            String[] nameArray = survivor.NAME.split(" ");
            if (nameArray[0].equalsIgnoreCase(name)) {
                return survivor;
            }
        }

        return null;
    }

    @NotNull
    public static Survivors random() {
        Survivors[] survivors = values();
        int index = ThreadLocalRandom.current().nextInt(1, survivors.length);
        return survivors[index];
    }

    @NotNull
    @Override
    public String getName() {
        return NAME;
    }

    @NotNull
    @Override
    public InputStream getImage() {
        String fileName = "/" + NAME.toLowerCase() + ".png";
        try {
            return new FileInputStream(IMAGES_PATH + fileName);
        } catch (FileNotFoundException e) {
            BanterBot4J.warn("Image for DBD survivor " + NAME + " not found", e);
            throw new IllegalStateException("Image for " + NAME + " not found", e);
        }
    }

}
