package com.github.drsmugleaf.pokemon2.generations.ii.species;

import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon2.generations.i.species.SpeciesI;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class SpeciesII<T extends SpeciesII<T>> extends SpeciesI<T> {

    public SpeciesII(SpeciesBuilder<T> builder) {
        super(builder);
    }

}
