package com.github.drsmugleaf.pokemon2.generations.i.species;

import com.github.drsmugleaf.pokemon2.base.species.Species;
import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class SpeciesI<T extends SpeciesI<T>> extends Species<T> {

    public SpeciesI(SpeciesBuilder<T> builder) {
        super(builder);
    }

}
