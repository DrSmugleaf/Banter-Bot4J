package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public class API {

    @Nonnull
    private static final String PATH = "https://dennisreep.nl/dbd/api/v2/";

    @Nonnull
    private static final Gson GSON = new GsonBuilder().create();

    @Nonnull
    private static JsonObject getResponse(@Nonnull final String endpoint) {
        final String URL = PATH + endpoint;

        String body;
        try {
            body = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
        } catch (IOException e) {
            throw new APIException("Error connecting to " + URL, e);
        }

        JsonParser parser = new JsonParser();
        return parser.parse(body).getAsJsonObject();
    }

    @Nonnull
    public static List<KillerPerk> getKillerPerkData() {
        JsonArray json = getResponse("getKillerPerkData/").get("KillerPerk").getAsJsonArray();
        return GSON.fromJson(json, new TypeToken<ArrayList<KillerPerk>>(){}.getType());
    }

}
