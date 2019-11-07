package com.github.drsmugleaf.pokemon2.generations.iii.species;

import com.github.drsmugleaf.pokemon2.base.external.Smogon;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon2.generations.i.species.PokedexI;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.Ability;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.IAbility;
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
public class PokedexIII<T extends SpeciesIII<T>> extends Pokedex<T> {

    public PokedexIII(IGeneration generation, Function<SpeciesBuilderIII<T>, T> constructor) {
        super(getAll(generation, constructor));
    }

    private static <T extends ISpecies<T>> Map<String, T> getAll(
            IGeneration gen,
            Function<SpeciesBuilderIII<T>, T> constructor
    ) {
        Map<String, T> species = new HashMap<>();
        Smogon smogon = new Smogon(gen);
        JSONArray pokemons = smogon.getSpecies();

        for (int i = 0; i < pokemons.length(); i++) {
            JSONObject pokemon = pokemons.getJSONObject(i);
            SpeciesBuilderIII<T> builder = toBuilder(gen, constructor, pokemon, species);
            species.put(builder.getName(), constructor.apply(builder));
        }

        return species;
    }

    public static <T extends ISpecies<T>> SpeciesBuilderIII<T> toBuilder(
            IGeneration gen,
            Function<SpeciesBuilderIII<T>, T> constructor,
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

        SpeciesBuilder<T> builderI = PokedexI.toBuilder(gen, pokemon, species);
        return new SpeciesBuilderIII<>(builderI, constructor).setAbilities(abilities);
    }

}
