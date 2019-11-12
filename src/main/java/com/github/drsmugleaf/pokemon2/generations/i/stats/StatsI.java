package com.github.drsmugleaf.pokemon2.generations.i.stats;

import com.github.drsmugleaf.pokemon2.base.species.stat.IStat;
import org.jetbrains.annotations.Contract;

/**
 * Created by DrSmugleaf on 09/11/2019
 */
public enum StatsI implements IStat {

    HP("HP", "HP", true),
    ATTACK("Attack", "Atk", true),
    DEFENSE("Defense", "Def", true),
    SPEED("Speed", "Spd", true),
    SPECIAL("Special", "Spe", true),
    EVASION("Evasion", "Eva", false),
    ACCURACY("Accuracy", "Acc", false);

    private final String NAME;
    private final String ABBREVIATION;
    private final boolean IS_PERMANENT;

    StatsI(String name, String abbreviation, boolean isPermanent) {
        NAME = name;
        ABBREVIATION = abbreviation;
        IS_PERMANENT = isPermanent;
    }

    @Contract(pure = true)
    @Override
    public String getAbbreviation() {
        return ABBREVIATION;
    }

    @Contract(pure = true)
    @Override
    public boolean isPermanent() {
        return IS_PERMANENT;
    }

    @Contract(pure = true)
    @Override
    public String getName() {
        return NAME;
    }

}
