package com.github.drsmugleaf.pokemon2.base.external;

import com.github.drsmugleaf.pokemon.external.ParsingException;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.type.TypeBuilder;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.Ability;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.IAbility;
import com.github.drsmugleaf.pokemon2.generations.iii.nature.INature;
import com.github.drsmugleaf.pokemon2.generations.iii.nature.Nature;
import com.google.common.collect.ImmutableSet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    private JSONArray get(String array) {
        return getRpcs()
                .getJSONArray(1)
                .getJSONObject(1)
                .getJSONArray(array);
    }

    public IGeneration getGeneration() {
        return GENERATION;
    }

    public JSONArray getSpecies() {
        return get("pokemon");
    }

    public TypeBuilder getTypes() {
        JSONArray jsonTypes = get("types");
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

    public Map<String, INature> getNatures() {
        Map<String, INature> natures = new HashMap<>();
        JSONArray naturesArray = get("natures");

        for (int i = 0; i < naturesArray.length(); i++) {
            JSONObject natureObject = naturesArray.getJSONObject(i);
            String name = natureObject.getString("name");
            double def = natureObject.getDouble("def");
            double spa = natureObject.getDouble("spa");
            double spd = natureObject.getDouble("spd");
            double hp = natureObject.getDouble("hp");
            double atk = natureObject.getDouble("atk");
            double spe = natureObject.getDouble("spe");

            Map<PermanentStat, Double> multipliers = new HashMap<>();
            multipliers.put(PermanentStat.DEFENSE, def);
            multipliers.put(PermanentStat.SPECIAL_ATTACK, spa);
            multipliers.put(PermanentStat.SPECIAL_DEFENSE, spd);
            multipliers.put(PermanentStat.HP, hp);
            multipliers.put(PermanentStat.ATTACK, atk);
            multipliers.put(PermanentStat.SPEED, spe);

            INature nature = new Nature(name, multipliers);
            natures.put(name, nature);
        }

        return natures;
    }

    public Map<String, IAbility> getAbilities() {
        Map<String, IAbility> abilities = new HashMap<>();
        JSONArray abilitiesArray = get("abilities");

        for (int i = 0; i < abilitiesArray.length(); i++) {
            JSONObject natureObject = abilitiesArray.getJSONObject(i);
            String name = natureObject.getString("name");
            IAbility ability = new Ability(name);

            abilities.put(name, ability);
        }

        return abilities;
    }

    public void printPokemonsAsEnums(IGeneration gen, IGeneration... minus) {
        HashMap<String, ISpecies<?>> species = new HashMap<>(gen.getPokedex().get());

        for (IGeneration minusGen : minus) {
            ImmutableSet<String> names = minusGen.getPokedex().get().keySet();
            species.keySet().removeAll(names);
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

            System.out.println("(\"" + name + "\"),");
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
