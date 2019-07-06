package com.github.drsmugleaf.pokemon.external.smogon;

import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.external.ParsingException;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon2.base.type.IType;
import com.github.drsmugleaf.pokemon2.base.type.TypeBuilder;
import com.github.drsmugleaf.pokemon2.generations.i.GenerationI;
import com.github.drsmugleaf.pokemon2.generations.i.ability.Ability;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * Created by DrSmugleaf on 06/06/2017.
 */
public class SmogonParser {

    private static final String SMOGON_URL = "https://www.smogon.com/dex/";

    public static void main(String[] args) {
        Generation generationI = GenerationI.get();
        System.out.println(generationI);
    }

    public static JSONArray getPokemons(IGeneration gen) {
        Connection connection = Jsoup.connect(SMOGON_URL + gen.getAbbreviation() + "/pokemon/");
        Document doc;
        try {
            doc = connection.get();
        } catch (IOException e) {
            throw new ParsingException("Error getting pokemons for gen: " + gen.getAbbreviation(), e);
        }

        String docString = doc
                .getElementsByTag("script")
                .first()
                .dataNodes()
                .get(0)
                .getWholeData()
                .replace("dexSettings = ", "");

        return new JSONObject(docString)
                .getJSONArray("injectRpcs")
                .getJSONArray(1)
                .getJSONObject(1)
                .getJSONArray("pokemon");
    }

    public static Map<String, IType> getTypes(IGeneration gen, TypeBuilder builder) {
        Connection connection = Jsoup.connect(SMOGON_URL + gen.getAbbreviation() + "/pokemon/");
        Document doc;
        try {
            doc = connection.get();
        } catch (IOException e) {
            throw new ParsingException("Error getting types for gen: " + gen.getAbbreviation(), e);
        }

        String docString = doc
                .getElementsByTag("script")
                .first()
                .dataNodes()
                .get(0)
                .getWholeData()
                .replace("dexSettings = ", "");

        JSONArray jsonTypes = new JSONObject(docString)
                .getJSONArray("injectRpcs")
                .getJSONArray(1)
                .getJSONObject(1)
                .getJSONArray("types");

        for (int i = 0; i < jsonTypes.length(); i++) {
            JSONObject jsonType = jsonTypes.getJSONObject(i);
            String name = jsonType.getString("name");
            JSONArray attackEffectiveness = jsonType.getJSONArray("atk_effectives");

            for (int j = 0; j < attackEffectiveness.length(); j++) {
                JSONArray effectiveType = attackEffectiveness.getJSONArray(j);
                String effectiveName = effectiveType.getString(0);
                TypeBuilder.SpecificTypeBuilder type = builder.get(effectiveName);
                double effectiveMultiplier = effectiveType.getDouble(1);

                if (effectiveMultiplier == 0) {
                    type.addImmunity(name);
                } else if (effectiveMultiplier == 0.5) {
                    type.addResistance(name);
                } else if (effectiveMultiplier == 2) {
                    type.addWeakness(name);
                }
            }
        }

        return builder.build();
    }

    public static void printPokemonsAsEnums(IGeneration gen, IGeneration... minus) {
        JSONArray json = getPokemons(gen);
        Map<String, JSONObject> pokemons = new TreeMap<>();

        for (int i = 0; i < json.length(); i++) {
            JSONObject pokemon = json.getJSONObject(i);
            String name = pokemon.getString("name");
            pokemons.put(name, pokemon);
        }

        for (IGeneration minusGen : minus) {
            json = getPokemons(minusGen);

            for (int i = 0; i < json.length(); i++) {
                JSONObject pokemon = json.getJSONObject(i);
                String name = pokemon.getString("name");
                pokemons.remove(name);
            }
        }

        for (JSONObject pokemon : pokemons.values()) {
            String name = pokemon.getString("name");
            JSONArray alts = pokemon.getJSONArray("alts");

            for (int j = 0; j < alts.length(); j++) {
                JSONObject alt = alts.getJSONObject(j);
                String suffix = alt.getString("suffix");

                System.out.print(
                        name
                                .replace(" ", "_")
                                .replace("-", "_")
                                .replace("'", "")
                                .replace(".", "")
                                .replace("%", "")
                                .replace(":", "")
                                .toUpperCase()
                );

                if (!suffix.isEmpty()) {
                    System.out.print(
                            "_" +
                                    suffix
                                            .replace("-", "_")
                                            .toUpperCase()
                    );
                }

                System.out.print("(\"" + name);
                if (!suffix.isEmpty()) {
                    System.out.print("-" + suffix);
                }
                System.out.println("\"),");
            }
        }
    }

    public static void printAbilitiesAsEnums(JSONArray abilities) {
        for (int i = 0; i < abilities.length(); i++) {
            String name = abilities.getJSONObject(i).getString("name");

            System.out.print(name
                    .replace(" ", "_")
                    .replace("-", "_")
                    .replace("'", "")
                    .toUpperCase()
            );

            System.out.println("(\"" + name + "\"),");
        }
    }

    public static void printItemsAsEnums(JSONArray items) {
        for (int i = 0; i < items.length(); i++) {
            String name = items.getJSONObject(i).getString("name");

            System.out.print(name
                    .replace(" ", "_")
                    .replace("-", "_")
                    .replace("'", "")
                    .toUpperCase()
            );
            System.out.println("(\"" + name + "\"),");
        }
    }

    public static Map<String, ISpecies> getSpecies(IGeneration generation, Function<SpeciesBuilder, ISpecies> constructor) {
        Map<String, ISpecies> species = new HashMap<>();
        JSONArray pokemons;
        pokemons = SmogonParser.getPokemons(generation);

        for (int i = 0; i < pokemons.length(); i++) {
            JSONObject jsonPokemon = pokemons.getJSONObject(i);
            String name = jsonPokemon.getString("name");

            JSONArray jsonGenerations = jsonPokemon.getJSONArray("genfamily");
            List<IGeneration> genFamilies = new ArrayList<>();
            for (int j = 0; j < jsonGenerations.length(); j++) {
                String generationName = jsonGenerations.getString(j);
                IGeneration genFamily = IGeneration.from(generationName);
                genFamilies.add(genFamily);
            }

            JSONArray alts = jsonPokemon.getJSONArray("alts");
            for (int j = 0; j < alts.length(); j++) {
                JSONObject alt = alts.getJSONObject(j);
                String suffix = alt.getString("suffix");
                if (!suffix.isEmpty()) {
                    name = name.concat("-" + suffix);
                }

                int hp = alt.getInt("hp");
                int attack = alt.getInt("atk");
                int defense = alt.getInt("def");
                int specialAttack = alt.getInt("spa");
                int specialDefense = alt.getInt("spd");
                int speed = alt.getInt("spe");
                double weight = alt.getDouble("weight");
                double height = alt.getDouble("height");

                Collection<Ability> abilities = Ability.fromAlt(alt).values();

                List<? extends IType> types = generation.getTypes().fromAlt(alt);

                JSONArray jsonTiers = alt.getJSONArray("formats");
                List<Tier> tiers = new ArrayList<>();
                for (int k = 0; k < jsonTiers.length(); k++) {
                    Tier tier = Tier.getTier(jsonTiers.getString(k));
                    tiers.add(tier);
                }

                // TODO: 05-Jul-19 Add evolutions
                SpeciesBuilder builder = new SpeciesBuilder()
                        .setName(name)
                        .setGenerations(genFamilies)
                        .addStat(PermanentStat.HP, hp)
                        .addStat(PermanentStat.ATTACK, attack)
                        .addStat(PermanentStat.DEFENSE, defense)
                        .addStat(PermanentStat.SPECIAL_ATTACK, specialAttack)
                        .addStat(PermanentStat.SPECIAL_DEFENSE, specialDefense)
                        .addStat(PermanentStat.SPEED, speed)
                        .setWeight(weight)
                        .setHeight(height)
                        .setAbilities(abilities)
                        .setTypes(types)
                        .setTiers(tiers);

                species.put(name, constructor.apply(builder));
            }
        }

        return species;
    }

}
