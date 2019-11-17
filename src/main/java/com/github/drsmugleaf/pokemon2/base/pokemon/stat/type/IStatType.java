package com.github.drsmugleaf.pokemon2.base.pokemon.stat.type;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public interface IStatType extends Nameable {

    String getAbbreviation();
    boolean isPermanent();

}
