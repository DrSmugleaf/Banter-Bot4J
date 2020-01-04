package com.github.drsmugleaf.pokemon.generations.iii.pokemon.species;

import com.github.drsmugleaf.pokemon.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon.base.pokemon.species.Pokedex;
import com.github.drsmugleaf.pokemon.base.pokemon.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon.generations.i.pokemon.species.PokedexI;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.ability.Ability;
import com.github.drsmugleaf.pokemon.generations.iii.pokemon.ability.IAbility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class PokedexIII<T extends BaseSpeciesIII<T>> extends Pokedex<T> {

    public PokedexIII(IGeneration generation, Function<SpeciesBuilderIII<T>, T> constructor) {
        super(getAll(generation, constructor));
    }

    private static <T extends ISpecies> Map<String, T> getAll(
            IGeneration gen,
            Function<SpeciesBuilderIII<T>, T> constructor
    ) {
        Map<String, T> species = new HashMap<>();
        JSONArray allSpecies = gen.getSmogon().getSpecies();

        for (int i = 0; i < allSpecies.length(); i++) {
            JSONObject speciesJson = allSpecies.getJSONObject(i);
            SpeciesBuilderIII<T> builder = toBuilder(gen, speciesJson, species);
            species.put(builder.getName(), constructor.apply(builder));
        }

        return species;
    }

    public static <T extends ISpecies> SpeciesBuilderIII<T> toBuilder(
            IGeneration gen,
            JSONObject pokemon,
            Map<String, T> species
    ) {
        JSONArray jsonAbilities = pokemon.getJSONArray("abilities");
        List<IAbility> abilities = jsonAbilities
                .toList()
                .stream()
                .map(o -> (String) o)
                .map(Ability::new)
                .collect(Collectors.toList());

        SpeciesBuilder<T, ?> builderI = PokedexI.toBuilder(gen, pokemon, species);
        return new SpeciesBuilderIII<>(builderI).setAbilities(abilities);
    }

}
