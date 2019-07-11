package com.github.drsmugleaf.pokemon2.generations.v.species;

import com.github.drsmugleaf.pokemon2.generations.iii.species.SpeciesBuilderIII;
import com.github.drsmugleaf.pokemon2.generations.iv.species.SpeciesIV;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class SpeciesV<T extends SpeciesV<T>> extends SpeciesIV<T> {

    public SpeciesV(SpeciesBuilderIII<T> builder) {
        super(builder);
    }

}
