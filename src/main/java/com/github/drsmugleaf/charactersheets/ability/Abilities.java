package com.github.drsmugleaf.charactersheets.ability;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Abilities {

    private final ImmutableMap<String, Ability> ABILITIES;

    public Abilities(Map<String, Ability> abilities) {
        ABILITIES = ImmutableMap.copyOf(abilities);
    }

    public ImmutableMap<String, Ability> get() {
        return ABILITIES;
    }

    public Ability get(String name) {
        return ABILITIES.get(name);
    }

}
