package com.github.drsmugleaf.pokemon2.generations.iv.species;

import com.github.drsmugleaf.pokemon2.generations.iii.species.SpeciesBuilderIII;
import com.github.drsmugleaf.pokemon2.generations.iii.species.SpeciesIII;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class SpeciesIV<T extends SpeciesIV<T>> extends SpeciesIII<T> {

    public SpeciesIV(SpeciesBuilderIII<T> builder) {
        super(builder);
    }

}
