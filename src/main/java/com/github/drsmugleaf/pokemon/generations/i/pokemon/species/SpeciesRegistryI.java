package com.github.drsmugleaf.pokemon.generations.i.pokemon.species;

import com.github.drsmugleaf.pokemon.base.format.IFormat;
import com.github.drsmugleaf.pokemon.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon.base.pokemon.species.ISpecies;
import com.github.drsmugleaf.pokemon.base.pokemon.species.SpeciesRegistry;
import com.github.drsmugleaf.pokemon.base.pokemon.type.IType;
import com.github.drsmugleaf.pokemon.generations.i.pokemon.stat.StatsI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 06/07/2019
 */
public class SpeciesRegistryI<T extends ISpecies> extends SpeciesRegistry<T> {

    public SpeciesRegistryI(Collection<T> set) {
        super(set);
    }

    public SpeciesRegistryI(IGeneration generation, Function<SpeciesBuilderI<T>, T> constructor) {
        super(getAll(generation, constructor));
    }

    public static <T extends ISpecies> Map<String, T> getAll(
            IGeneration gen,
            Function<SpeciesBuilderI<T>, T> constructor
    ) {
        Map<String, T> species = new HashMap<>();
        JSONArray allSpecies = gen.getSmogon().getSpecies();

        for (int i = 0; i < allSpecies.length(); i++) {
            JSONObject speciesJson = allSpecies.getJSONObject(i);
            SpeciesBuilderI<T> builder = toBuilder(gen, speciesJson, species);
            species.put(builder.getName(), constructor.apply(builder));
        }

        return species;
    }

    public static <T extends ISpecies> SpeciesBuilderI<T> toBuilder(
            IGeneration gen,
            JSONObject pokemon,
            Map<String, T> species
    ) {
        String name = pokemon.getString("name");
        JSONObject oob = pokemon;
        List<String> alts = new ArrayList<>();
        if (!pokemon.isNull("oob")) {
            oob = pokemon.getJSONObject("oob");
            JSONArray altsJson = oob.getJSONArray("alts");

            for (int i = 0; i < altsJson.length(); i++) {
                String altName = altsJson.getString(i);
                alts.add(altName);
            }
        }

        List<String> generationNames = new ArrayList<>();
        if (oob.has("genfamily")) {
            JSONArray jsonGenerations = oob.getJSONArray("genfamily");
            for (int i = 0; i < jsonGenerations.length(); i++) {
                String generationName = jsonGenerations.getString(i);
                generationNames.add(generationName);
            }
        } else {
            for (T previousSpecies : species.values()) {
                if (!previousSpecies.getAlts().contains(name)) {
                    continue;
                }

                generationNames.addAll(previousSpecies.getGenerations().getRaw());
            }
        }

        int hp = pokemon.getInt("hp");
        int attack = pokemon.getInt("atk");
        int defense = pokemon.getInt("def");
        int specialAttack = pokemon.getInt("spa");
        int speed = pokemon.getInt("spe");
        double weight = pokemon.getDouble("weight");
        double height = pokemon.getDouble("height");

        List<IType> types = gen.getTypes().fromJson(pokemon);

        JSONArray jsonFormats = pokemon.getJSONArray("formats");
        Set<IFormat> formats = new HashSet<>();
        for (int i = 0; i < jsonFormats.length(); i++) {
            String jsonFormat = jsonFormats.getString(i);
            IFormat format = gen.getFormats().getByAbbreviation().get(jsonFormat);
            formats.add(format);
        }

        // TODO: 05-Jul-19 Add evolutions
        // TODO: 07-Nov-19 Add genders
        return new SpeciesBuilderI<T>()
                .setName(name)
                .setGenerations(generationNames)
                .addStat(StatsI.HP, hp)
                .addStat(StatsI.ATTACK, attack)
                .addStat(StatsI.DEFENSE, defense)
                .addStat(StatsI.SPECIAL, specialAttack)
                .addStat(StatsI.SPEED, speed)
                .setWeight(weight)
                .setHeight(height)
                .setTypes(types)
                .setTiers(formats)
                .setAlts(alts);
    }

}
