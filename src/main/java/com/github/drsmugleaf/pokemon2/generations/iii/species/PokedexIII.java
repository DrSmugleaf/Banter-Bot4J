package com.github.drsmugleaf.pokemon2.generations.iii.species;

import com.github.drsmugleaf.pokemon.battle.Tier;
import com.github.drsmugleaf.pokemon.stats.PermanentStat;
import com.github.drsmugleaf.pokemon2.base.external.Smogon;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import com.github.drsmugleaf.pokemon2.base.species.ISpecies;
import com.github.drsmugleaf.pokemon2.base.species.Pokedex;
import com.github.drsmugleaf.pokemon2.base.type.IType;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.Ability;
import com.github.drsmugleaf.pokemon2.generations.iii.ability.IAbility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 11/07/2019
 */
public class PokedexIII<T extends SpeciesIII> extends Pokedex<T> {

    public PokedexIII(IGeneration generation, Function<SpeciesBuilderIII<T>, T> constructor) {
        super(getAll(generation, constructor));
    }
    private static <T extends ISpecies> Map<String, T> getAll(
            IGeneration gen,
            Function<SpeciesBuilderIII<T>, T> constructor
    ) {
        Map<String, T> species = new HashMap<>();
        Smogon smogon = new Smogon(gen);
        JSONArray pokemons = smogon.getSpecies();

        for (int i = 0; i < pokemons.length(); i++) {
            JSONObject jsonPokemon = pokemons.getJSONObject(i);
            String name = jsonPokemon.getString("name");

            JSONArray jsonGenerations = jsonPokemon.getJSONArray("genfamily");
            List<String> generations = new ArrayList<>();
            for (int j = 0; j < jsonGenerations.length(); j++) {
                String generation = jsonGenerations.getString(j);
                generations.add(generation);
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


                JSONArray jsonAbilities = alt.getJSONArray("abilities");
                List<IAbility> abilities = jsonAbilities
                        .toList()
                        .stream()
                        .map(o -> (String) o)
                        .map(Ability::new).collect(Collectors.toList());

                List<? extends IType> types = gen.getTypes().fromAlt(alt);

                JSONArray jsonTiers = alt.getJSONArray("formats");
                List<Tier> tiers = new ArrayList<>();
                for (int k = 0; k < jsonTiers.length(); k++) {
                    Tier tier = Tier.getTier(jsonTiers.getString(k));
                    tiers.add(tier);
                }

                // TODO: 05-Jul-19 Add evolutions
                SpeciesBuilderIII<T> builder = new SpeciesBuilderIII<>(constructor)
                        .setName(name)
                        .setGenerations(generations)
                        .addStat(PermanentStat.HP, hp)
                        .addStat(PermanentStat.ATTACK, attack)
                        .addStat(PermanentStat.DEFENSE, defense)
                        .addStat(PermanentStat.SPECIAL_ATTACK, specialAttack)
                        .addStat(PermanentStat.SPECIAL_DEFENSE, specialDefense)
                        .addStat(PermanentStat.SPEED, speed)
                        .setWeight(weight)
                        .setHeight(height)
                        .setTypes(types)
                        .setTiers(tiers)
                        .setAbilities(abilities);

                species.put(name, constructor.apply(builder));
            }
        }

        return species;
    }

}
