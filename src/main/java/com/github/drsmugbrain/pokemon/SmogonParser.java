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
        JSONObject obj = new JSONObject(doc.getElementsByTag("script").first().dataNodes().get(0).getWholeData().replace("dexSettings = ", ""));
        JSONArray pokemon = obj.getJSONArray("injectRpcs").getJSONArray(1).getJSONObject(1).getJSONArray("pokemon");

        SmogonParser.parsePokemon(pokemon);
    }

    private static void parsePokemon(JSONArray pokemonJSONArray) {
        for (int i = 0; i < pokemonJSONArray.length(); i++) {
            JSONObject pokemon = pokemonJSONArray.getJSONObject(i);
            JSONObject stats = pokemon.getJSONArray("alts").getJSONObject(0);
            String name = pokemon.getString("name");
            Type[] types = Type.getTypes(stats.getJSONArray("types"));

            Pokemon.createBasePokemon(name, types, Pokemon.parseStats(stats));
        }
    }

}
