package com.github.drsmugleaf.pokemon2.base.species.stats;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public interface IStat extends Nameable {

    String getAbbreviation();
    boolean isPermanent();

}
