package com.github.drsmugleaf.pokemon.generations.i.pokemon.species;

import com.github.drsmugleaf.pokemon.base.builder.IBuilder;
import com.github.drsmugleaf.pokemon.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon.base.pokemon.species.SpeciesBuilder;

/**
 * Created by DrSmugleaf on 08/11/2019
 */
public class SpeciesBuilderI<T extends ISpecies> extends SpeciesBuilder<T, SpeciesBuilderI<T>> implements IBuilder<SpeciesBuilderI<T>> {}
