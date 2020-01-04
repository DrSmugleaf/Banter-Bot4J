package com.github.drsmugleaf.pokemon.generations.ii.pokemon.gender;

import com.github.drsmugleaf.pokemon.base.nameable.Nameable;

/**
 * Created by DrSmugleaf on 13/11/2019
 */
public interface IGender extends Nameable {

    String getAbbreviation();
    boolean isOpposite(IGender other);

}
