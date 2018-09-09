package com.github.drsmugleaf.pokemon.external;

import com.github.drsmugleaf.BanterBot4J;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * Created by DrSmugleaf on 06/06/2017.
 */
public class SmogonParser {

    @Nonnull
    private static final String SMOGON_URL = "http://www.smogon.com/dex/sm/pokemon/";

    @Nonnull
    public static JSONArray getPokemons() throws IOException {
        Document doc;
        try {
            doc = Jsoup.connect(SMOGON_URL).get();
        } catch (IOException e) {
            BanterBot4J.LOGGER.error("Error connecting to Smogon", e);
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

    public static void printPokemonsAsEnums() {
        JSONArray pokemons;
        try {
            pokemons = getPokemons();
        } catch (IOException e) {
            throw new ParsingException("Error getting pokemons", e);
        }

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

    public static void printAbilitiesAsEnums(@Nonnull JSONArray abilities) {
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

    public static void printItemsAsEnums(@Nonnull JSONArray items) {
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
