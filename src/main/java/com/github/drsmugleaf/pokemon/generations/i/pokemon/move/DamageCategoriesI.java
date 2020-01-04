package com.github.drsmugleaf.pokemon.generations.i.pokemon.move;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.base.pokemon.move.IDamageCategory;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon.generations.i.pokemon.stat.StatsI;

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
    private final IStatType ATTACK_STAT;
    @Nullable
    private final IStatType DEFENSE_STAT;

    DamageCategoriesI(String name, boolean doesDamage, @Nullable IStatType attackStat, @Nullable IStatType defenseStat) {
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
    public IStatType getAttackStat() {
        return ATTACK_STAT;
    }

    @Nullable
    @Override
    public IStatType getDefenseStat() {
        return DEFENSE_STAT;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
