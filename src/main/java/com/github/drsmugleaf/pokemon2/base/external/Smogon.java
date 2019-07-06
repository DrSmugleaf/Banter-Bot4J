package com.github.drsmugleaf.pokemon2.base.external;

import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.external.ParsingException;
import com.github.drsmugleaf.pokemon.external.smogon.SmogonParser;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.SpeciesBuilder;
import com.github.drsmugleaf.pokemon2.base.type.IType;
import com.github.drsmugleaf.pokemon2.base.type.TypeBuilder;
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
 * Created by DrSmugleaf on 06/07/2019
 */
public class Smogon {

    private static final String SMOGON_URL = "https://www.smogon.com/dex/";

    private final IGeneration GENERATION;

    public Smogon(IGeneration generation) {
        GENERATION = generation;
    }

    private Connection connect() {
        return Jsoup.connect(SMOGON_URL + GENERATION.getAbbreviation() + "/pokemon/");
    }

    private Document getDocument() {
        Connection connection = connect();
        Document document;
        try {
            document = connection.get();
        } catch (IOException e) {
            throw new ParsingException("Error getting document for generation: " + GENERATION.getAbbreviation(), e);
        }

        return document;
    }

    private JSONArray getRpcs() {
        Document document = getDocument();

        String json = document
                .getElementsByTag("script")
                .first()
                .dataNodes()
                .get(0)
                .getWholeData()
                .replace("dexSettings = ", "");

        return new JSONObject(json).getJSONArray("injectRpcs");
    }

    public IGeneration getGeneration() {
        return GENERATION;
    }

    public <T extends ISpecies> Map<String, T> getSpecies(Function<SpeciesBuilder<T>, T> constructor) {
        Map<String, T> species = new HashMap<>();
        JSONArray pokemons;
        pokemons = SmogonParser.getPokemons(GENERATION);

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

                List<? extends IType> types = GENERATION.getTypes().fromAlt(alt);

                JSONArray jsonTiers = alt.getJSONArray("formats");
                List<Tier> tiers = new ArrayList<>();
                for (int k = 0; k < jsonTiers.length(); k++) {
                    Tier tier = Tier.getTier(jsonTiers.getString(k));
                    tiers.add(tier);
                }

                // TODO: 05-Jul-19 Add evolutions
                SpeciesBuilder<T> builder = new SpeciesBuilder<>(constructor)
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

    public TypeBuilder getTypes() {
        JSONArray jsonTypes = getRpcs()
                .getJSONArray(1)
                .getJSONObject(1)
                .getJSONArray("types");

        TypeBuilder builder = new TypeBuilder();
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

        return builder;
    }

    public void printPokemonsAsEnums(Function<SpeciesBuilder<ISpecies>, ISpecies> constructor, IGeneration gen, IGeneration... minus) {
        Map<String, ISpecies> species = new TreeMap<>(getSpecies(constructor));

        for (IGeneration minusGen : minus) {
            Map<String, ISpecies> minusMap = new Smogon(minusGen).getSpecies(constructor);
            species.keySet().removeAll(minusMap.keySet());
        }

        for (ISpecies pokemon : species.values()) {
            String name = pokemon.getName();
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

            String suffix = pokemon.getSuffix();
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

    public void printAbilitiesAsEnums(JSONArray abilities) {
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

    public void printItemsAsEnums(JSONArray items) {
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

}
