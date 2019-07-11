package com.github.drsmugleaf.charactersheets.ability;

import com.github.drsmugleaf.charactersheets.Nameable;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Ability implements Nameable {

    private final String NAME;
    private final String DESCRIPTION;

    public Ability(String name, String description) {
        NAME = name;
        DESCRIPTION = description;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

}
