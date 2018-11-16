package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.deadbydaylight.ICharacter;
import com.github.drsmugleaf.deadbydaylight.Killers;
import com.github.drsmugleaf.deadbydaylight.Survivors;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 07/11/2018
 */
public class NameResolver {

    @NotNull
    private static final Map<String, String> KILLER_NAMES = new HashMap<>();

    @NotNull
    private static final Map<String, String> SURVIVOR_NAMES = new HashMap<>();

    static {
        registerKillers();
        registerSurvivors();
    }

    @NotNull
    private static <T extends Enum<T> & ICharacter> Map<String, String> register(Class<T> enumClass) {
        Map<String, String> names = new HashMap<>();
        List<String> namesSeen = new ArrayList<>();

        for (T character : enumClass.getEnumConstants()) {
            String name = character.getName().toLowerCase();
            if (namesSeen.contains(name)) {
                throw new IllegalStateException("Found a repeated full character name: " + name);
            }

            names.put(name, character.name());

            String[] nameArray = name.split(" ");
            if (nameArray[0].equals(name)) {
                continue;
            }

            name = nameArray[0];

            if (namesSeen.contains(name)) {
                names.remove(name);
            } else {
                names.put(name, character.name());
            }

            namesSeen.add(name);
        }

        return names;
    }

    private static void registerKillers() {
        Map<String, String> killers = register(Killers.class);
        KILLER_NAMES.putAll(killers);
    }

    private static void registerSurvivors() {
        Map<String, String> survivors = register(Survivors.class);
        SURVIVOR_NAMES.putAll(survivors);
    }

    @NotNull
    public static String resolveKillerName(@NotNull String name) {
        name = name.toLowerCase();

        if (!KILLER_NAMES.containsKey(name)) {
            throw new IllegalArgumentException("No killer found with name " + name);
        }

        return KILLER_NAMES.get(name);
    }

    @NotNull
    public static String resolveSurvivorName(@NotNull String name) {
        name = name.toLowerCase();

        if (!SURVIVOR_NAMES.containsKey(name)) {
            throw new IllegalArgumentException("No survivor found with name " + name);
        }

        return SURVIVOR_NAMES.get(name);
    }

}
