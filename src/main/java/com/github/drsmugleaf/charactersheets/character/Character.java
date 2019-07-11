package com.github.drsmugleaf.charactersheets.character;

import com.github.drsmugleaf.charactersheets.Nameable;
import com.github.drsmugleaf.charactersheets.ability.Abilities;
import com.github.drsmugleaf.charactersheets.stat.Stats;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class Character implements Nameable {

    private final String NAME;
    private final Stats STATS;
    private final Abilities ABILITIES;

    public Character(String name, Stats stats, Abilities abilities) {
        NAME = name;
        STATS = stats;
        ABILITIES = abilities;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public Stats getStats() {
        return STATS;
    }

    public Abilities getAbilities() {
        return ABILITIES;
    }

}
