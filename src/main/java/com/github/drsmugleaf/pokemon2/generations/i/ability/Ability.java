package com.github.drsmugleaf.pokemon2.generations.i.ability;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.pokemon.external.smogon.SmogonParser;
import com.github.drsmugleaf.pokemon2.base.ability.IAbility;
import com.github.drsmugleaf.pokemon2.base.generation.Generation;
import com.github.drsmugleaf.pokemon2.generations.i.GenerationI;
import com.google.common.collect.ImmutableMap;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 03/07/2019
 */
public class Ability implements IAbility<Ability> {

    private static final ImmutableMap<String, Ability> ABILITIES = ImmutableMap.copyOf(getAll());

    private final String NAME;

    private Ability(String name) {
        NAME = name;
    }

    private Ability(Ability ability) {
        this(ability.NAME);
    }

    private static Map<String, Ability> getAll() {
        Map<String, Ability> abilities = new HashMap<>();
        JSONArray pokemons;
        Generation generation = GenerationI.get();
        pokemons = SmogonParser.getPokemons(generation);

        for (int i = 0; i < pokemons.length(); i++) {
            JSONObject jsonPokemon = pokemons.getJSONObject(i);
            JSONArray alts = jsonPokemon.getJSONArray("alts");

            for (int j = 0; j < alts.length(); j++) {
                JSONObject alt = alts.getJSONObject(j);
                Map<String, Ability> altAbilities = fromAlt(alt);
                abilities.putAll(altAbilities);
            }
        }

        return abilities;
    }

    public static Map<String, Ability> fromAlt(JSONObject alt) {
        Map<String, Ability> abilities = new HashMap<>();
        JSONArray jsonAbilities = alt.getJSONArray("abilities");

        for (int i = 0; i < jsonAbilities.length(); i++) {
            String name = jsonAbilities.getString(i);
            Ability ability = new Ability(name);
            abilities.put(name, ability);
        }

        return abilities;
    }

    @Nullable
    public static Ability getAbility(String name) {
        Ability ability = ABILITIES.get(name);
        if (ability == null) {
            return null;
        }

        return new Ability(ability);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
