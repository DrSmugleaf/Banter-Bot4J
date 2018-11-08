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
    public static final String HOME_URL = "https://dennisreep.nl/dbd/";

    @Nonnull
    public static final String KILLER_PERKS_URL = "https://dennisreep.nl/dbd/perks/killer/";

    @Nonnull
    public static final String SURVIVOR_PERKS_URL = "https://dennisreep.nl/dbd/perks/survivor/";

    @Nonnull
    public static final String KILLERS_URL = "https://dennisreep.nl/dbd/killers/";

    @Nonnull
    public static final String KILLER_ROULETTE_URL = "https://dennisreep.nl/dbd/roulette/killer/";

    @Nonnull
    public static final String SURVIVOR_ROULETTE_URL = "https://dennisreep.nl/dbd/roulette/survivor/";

    @Nonnull
    public static final String LOGO_URL = "https://d1u5p3l4wpay3k.cloudfront.net/deadbydaylight_gamepedia_en/c/c7/Logo_dbd.png?version=fee2d7168217d35e77e33571b0023b2c";

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
