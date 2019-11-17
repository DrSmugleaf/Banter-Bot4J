package com.github.drsmugleaf.pokemon2.base.pokemon.move;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.type.IStatType;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IDamageCategory extends Nameable {

    boolean doesDamage();
    IStatType getAttackStat();
    IStatType getDefenseStat();

}
