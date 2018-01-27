package com.github.drsmugleaf.pokemon.external;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by DrSmugleaf on 06/06/2017.
 */
public class SmogonParser {

    public static void printPokemonsAsEnums(JSONArray pokemons) {
        for (int i = 0; i < pokemons.length(); i++) {
            JSONObject pokemon = pokemons.getJSONObject(i);
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

}
