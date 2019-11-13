package com.github.drsmugleaf.pokemon2.generations.i.pokemon.species;

import com.github.drsmugleaf.pokemon2.base.builder.IBuilder;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.pokemon.species.SpeciesBuilder;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public class SpeciesBuilderI<T extends ISpecies> extends SpeciesBuilder<T, SpeciesBuilderI<T>> implements IBuilder<SpeciesBuilderI<T>> {}
