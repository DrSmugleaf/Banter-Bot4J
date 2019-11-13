package com.github.drsmugleaf.pokemon2.generations.iii.species.stat.nature;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.species.stat.IStat;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public interface INature extends Nameable {

    double getStatMultiplier(IStat stat);

}
