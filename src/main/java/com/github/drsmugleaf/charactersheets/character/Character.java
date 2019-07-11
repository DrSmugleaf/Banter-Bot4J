package com.github.drsmugleaf.charactersheets.character;

import com.github.drsmugleaf.charactersheets.Nameable;
import com.github.drsmugleaf.charactersheets.stat.Stats;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Character implements Nameable {

    private final String NAME;
    private final Stats STATS;

    public Character(String name, Stats stats) {
        NAME = name;
        STATS = stats;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public Stats getStats() {
        return STATS;
    }

}
