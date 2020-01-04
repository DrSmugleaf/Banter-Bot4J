package com.github.drsmugleaf.pokemon.generations.iii.pokemon.stat.nature;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.IStatType;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public interface INature extends Nameable {

    double getStatMultiplier(IStatType stat);

}
