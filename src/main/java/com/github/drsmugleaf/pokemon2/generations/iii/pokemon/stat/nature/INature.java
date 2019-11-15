package com.github.drsmugleaf.pokemon2.generations.iii.pokemon.stat.nature;

import com.github.drsmugleaf.pokemon2.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon2.base.pokemon.stat.IBaseStat;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public interface INature extends Nameable {

    double getStatMultiplier(IBaseStat stat);

}
