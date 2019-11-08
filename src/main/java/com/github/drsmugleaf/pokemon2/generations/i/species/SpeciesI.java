package com.github.drsmugleaf.pokemon2.generations.i.species;

import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.ISpeciesBuilder;
import com.github.drsmugleaf.pokemon2.base.species.Species;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class SpeciesI extends Species<SpeciesI> implements ISpecies<SpeciesI> {

    public SpeciesI(ISpeciesBuilder<SpeciesI, ?> builder) {
        super(builder);
    }

}
