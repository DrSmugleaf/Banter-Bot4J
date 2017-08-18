package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.util.Bot;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by DrSmugleaf on 11/08/2017.
 */
public class PokemonData {

    public static JSONArray getPokemons() throws IOException {
        Document doc;
        try {
            doc = Jsoup.connect("http://www.smogon.com/dex/sm/pokemon/").get();
        } catch (IOException e) {
            Bot.LOGGER.error("Error connecting to Smogon", e);
            throw e;
        }

        String docString = doc
                .getElementsByTag("script")
                .first()
                .dataNodes()
                .get(0)
                .getWholeData()
                .replace("dexSettings = ", "");
        JSONObject obj = new JSONObject(docString).getJSONArray("injectRpcs").getJSONArray(1).getJSONObject(1);

        return obj.getJSONArray("pokemon");
    }

}