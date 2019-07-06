package com.github.drsmugleaf.pokemon.external.smogon;

import com.github.drsmugleaf.pokemon.external.ParsingException;
import com.github.drsmugleaf.pokemon2.base.generation.IGeneration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by DrSmugleaf on 06/06/2017.
 */
public class SmogonParser {

    private static final String SMOGON_URL = "https://www.smogon.com/dex/";

    public static void main(String[] args) {}

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

}
