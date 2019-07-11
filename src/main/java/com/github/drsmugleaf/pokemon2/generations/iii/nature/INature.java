package com.github.drsmugleaf.pokemon2.generations.iii.nature;

import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.Nameable;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public interface INature extends Nameable {

    double getStatMultiplier(PermanentStat stat);

}
