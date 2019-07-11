package com.github.drsmugleaf.charactersheets.ability;

import com.github.drsmugleaf.charactersheets.Nameable;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Abilities implements Nameable {

    private final String NAME;
    private final ImmutableMap<String, Ability> ABILITIES;

    public Abilities(String name, Map<String, Ability> abilities) {
        NAME = name;
        ABILITIES = ImmutableMap.copyOf(abilities);
    }

    @Override
    public String getName() {
        return NAME;
    }

    public ImmutableMap<String, Ability> get() {
        return ABILITIES;
    }

    public Ability get(String name) {
        return ABILITIES.get(name);
    }

}
