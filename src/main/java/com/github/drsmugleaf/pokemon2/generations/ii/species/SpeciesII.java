package com.github.drsmugleaf.pokemon2.generations.ii.species;

import com.github.drsmugleaf.pokemon.external.smogon.SmogonParser;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon2.generations.i.species.SpeciesI;
import com.github.drsmugleaf.pokemon2.generations.ii.GenerationII;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.Contract;

import java.util.Map;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public class SpeciesII extends SpeciesI {

    private static final ImmutableMap<String, ISpecies> SPECIES = ImmutableMap.copyOf(getAll());

    protected SpeciesII(SpeciesBuilder builder) {
        super(builder);
    }

    protected static Map<String, ISpecies> getAll() {
        return SmogonParser.getSpecies(GenerationII.get(), SpeciesII::new);
    }

    @Contract(pure = true)
    public static ImmutableMap<String, ISpecies> all() {
        return SPECIES;
    }

    @Override
    public ImmutableMap<String, ISpecies> getSpecies() {
        return all();
    }

}
