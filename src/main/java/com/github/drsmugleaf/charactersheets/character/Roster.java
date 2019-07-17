package com.github.drsmugleaf.charactersheets.character;

import com.github.drsmugleaf.charactersheets.Nameable;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Roster implements Nameable {

    private final String NAME;
    private final ImmutableMap<String, Character> CHARACTERS;

    public Roster(String name, Map<String, Character> characters) {
        NAME = name;
        CHARACTERS = ImmutableMap.copyOf(characters);
    }

    @Override
    public String getName() {
        return NAME;
    }

    public ImmutableMap<String, Character> getCharacters() {
        return CHARACTERS;
    }

}
