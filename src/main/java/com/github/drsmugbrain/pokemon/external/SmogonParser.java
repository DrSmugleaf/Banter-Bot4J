package com.github.drsmugbrain.pokemon.external;

import org.json.JSONArray;

/**
 * Created by DrSmugleaf on 06/06/2017.
 */
public class SmogonParser {

    private static void printPokemonsAsEnums(JSONArray pokemons) {
        for (int i = 0; i < pokemons.length(); i++) {
            String name = pokemons.getJSONObject(i).getString("name");

            System.out.print(name
                    .replace(" ", "_")
                    .replace("-", "_")
                    .replace("'", "")
                    .toUpperCase()
            );
            System.out.println("(\"" + name + "\"),");
        }
    }

    private static void printAbilitiesAsEnums(JSONArray abilities) {
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

    private static void printItemsAsEnums(JSONArray items) {
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
