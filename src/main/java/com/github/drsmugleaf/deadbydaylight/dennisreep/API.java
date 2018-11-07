package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public abstract class API {

    @Nonnull
    private static final String PATH = "https://dennisreep.nl/dbd/api/v2/";

    @Nonnull
    protected static final Gson GSON = new GsonBuilder().create();

    @Nonnull
    protected static JsonObject getResponse(@Nonnull final String endpoint) {
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

}
