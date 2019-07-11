package com.github.drsmugleaf.pokemon2.generations.vi.species;

import com.github.drsmugleaf.pokemon2.generations.iii.species.SpeciesBuilderIII;
import com.github.drsmugleaf.pokemon2.generations.v.species.SpeciesV;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class SpeciesVI<T extends SpeciesVI<T>> extends SpeciesV<T> {

    public SpeciesVI(SpeciesBuilderIII<T> builder) {
        super(builder);
    }

}
