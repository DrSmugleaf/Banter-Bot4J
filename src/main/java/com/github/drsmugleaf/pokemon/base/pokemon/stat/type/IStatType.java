package com.github.drsmugleaf.pokemon.base.pokemon.stat.type;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public interface IStatType extends Nameable {

    String getAbbreviation();
    boolean isPermanent();

}
