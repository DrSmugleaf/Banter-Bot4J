package com.github.drsmugleaf.pokemon2.generations.i.pokemon.move;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon2.base.pokemon.move.IDamageCategory;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IBaseStat;
import com.github.drsmugleaf.pokemon2.generations.i.pokemon.stat.StatsI;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public enum DamageCategoriesI implements IDamageCategory {

    PHYSICAL("Physical", true, StatsI.ATTACK, StatsI.DEFENSE),
    SPECIAL("Special", true, StatsI.SPECIAL, StatsI.SPECIAL),
    STATUS("Status", false, null, null);

    private final String NAME;
    private final boolean DOES_DAMAGE;
    @Nullable
    private final IBaseStat ATTACK_STAT;
    @Nullable
    private final IBaseStat DEFENSE_STAT;

    DamageCategoriesI(String name, boolean doesDamage, @Nullable IBaseStat attackStat, @Nullable IBaseStat defenseStat) {
        NAME = name;
        DOES_DAMAGE = doesDamage;
        ATTACK_STAT = attackStat;
        DEFENSE_STAT = defenseStat;
    }

    @Override
    public boolean doesDamage() {
        return DOES_DAMAGE;
    }

    @Nullable
    @Override
    public IBaseStat getAttackStat() {
        return ATTACK_STAT;
    }

    @Nullable
    @Override
    public IBaseStat getDefenseStat() {
        return DEFENSE_STAT;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
