package com.github.drsmugleaf.pokemon2.generations.iii.nature;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.species.stats.IStat;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public interface INature extends Nameable {

    double getStatMultiplier(IStat stat);

}
