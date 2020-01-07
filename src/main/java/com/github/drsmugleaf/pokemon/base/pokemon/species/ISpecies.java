package com.github.drsmugleaf.pokemon.base.pokemon.species;

import com.github.drsmugleaf.pokemon.base.format.FormatRegistry;
import com.github.drsmugleaf.pokemon.base.generation.GenerationRegistry;
import com.github.drsmugleaf.pokemon.base.nameable.Nameable;
import com.github.drsmugleaf.pokemon.base.pokemon.stat.type.IStatType;
import com.github.drsmugleaf.pokemon.base.pokemon.type.TypeRegistry;
import com.github.drsmugleaf.pokemon.base.registry.Columns;
import com.github.drsmugleaf.pokemon.base.registry.IColumns;
import com.github.drsmugleaf.pokemon.base.registry.IEntry;
import com.github.drsmugleaf.pokemon.generations.ii.pokemon.gender.IGender;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 01/07/2019
 */
public interface ISpecies extends IEntry {

    @Override
    default Map<String, String> export() {
        IColumns columns = new Columns();
        columns
                .put("name", getName())
                .put("generations", getGenerations().joining())
                .put("types", getSpeciesTypes().joining())
                .put("tiers", getTiers().joining());

        for (Map.Entry<IStatType, Integer> entry : getSpeciesStats().entrySet()) {
            String statName = entry.getKey().getName();
            Integer amount = entry.getValue();
            columns.put(statName, amount);
        }

        columns
                .put("evolutions", getEvolutions().joining())
                .put("height", getHeight())
                .put("weight", getWeight())
                .put("genders", getGenders().stream().map(Nameable::getName).collect(Collectors.joining(",")));

        return columns.get();
    }

    GenerationRegistry getGenerations();
    TypeRegistry getSpeciesTypes();
    FormatRegistry getTiers();
    ImmutableMap<IStatType, Integer> getSpeciesStats();
    SpeciesRegistry<? extends ISpecies> getEvolutions();
    double getHeight();
    double getWeight();
    ImmutableSet<IGender> getGenders();
    ImmutableSet<String> getAlts();

}
