package com.github.drsmugbrain.pokemon;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by DrSmugleaf on 06/06/2017.
 */
public class SmogonParser {

    public static void parse() throws IOException {
        Document doc = Jsoup.connect("http://www.smogon.com/dex/sm/pokemon/").get();
        String docString = doc
                .getElementsByTag("script")
                .first()
                .dataNodes()
                .get(0)
                .getWholeData()
                .replace("dexSettings = ", "");
        JSONObject obj = new JSONObject(docString).getJSONArray("injectRpcs").getJSONArray(1).getJSONObject(1);

        JSONArray moves = obj.getJSONArray("moves");
        SmogonParser.parseMoves(moves);
    }

    private static void parseMoves(JSONArray movesJSONArray) {
        for (int i = 0; i < movesJSONArray.length(); i++) {
            JSONObject move = movesJSONArray.getJSONObject(i);
            String name = move.getString("name");
            Type type = Type.getType(move.getString("type"));
            Category category = Category.getCategory(move.getString("category"));
            int power = move.getInt("power");
            int accuracy = move.getInt("accuracy");
            int pp = move.getInt("pp");

//            new BaseMove(name, type, category, power, accuracy, pp).createBaseMove();
        }
    }

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
