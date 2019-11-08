package com.github.drsmugleaf.pokemon2.generations.i.species;

import com.github.drsmugleaf.pokemon2.base.IBuilder;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public class SpeciesBuilderI<T extends ISpecies> extends SpeciesBuilder<T, SpeciesBuilderI<T>> implements IBuilder<SpeciesBuilderI<T>> {}
