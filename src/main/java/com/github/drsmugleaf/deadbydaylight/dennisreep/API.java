package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.BanterBot4J;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public abstract class API {

    public static final String HOME_URL = "https://dennisreep.nl/dbd/";

    public static final String KILLER_PERKS_URL = "https://dennisreep.nl/dbd/perks/killer/";

    public static final String SURVIVOR_PERKS_URL = "https://dennisreep.nl/dbd/perks/survivor/";

    public static final String KILLERS_URL = "https://dennisreep.nl/dbd/killers/";

    public static final String KILLER_ROULETTE_URL = "https://dennisreep.nl/dbd/roulette/killer/";

    public static final String SURVIVOR_ROULETTE_URL = "https://dennisreep.nl/dbd/roulette/survivor/";

    public static final String IMAGES_PATH = Objects.requireNonNull(API.class.getClassLoader().getResource("deadbydaylight")).getFile();

    public static final String PATH = "https://dennisreep.nl/dbd/api/v2/";

    protected static final Gson GSON = new GsonBuilder().create();

    public static InputStream getDBDLogo() {
        String fileName = "/logo.png";
        try {
            return new FileInputStream(IMAGES_PATH + fileName);
        } catch (FileNotFoundException e) {
            BanterBot4J.warn("DBD logo image not found", e);
            throw new IllegalStateException("DBD logo image not found", e);
        }
    }

    protected static JsonObject getResponse(String endpoint) {
        String URL = PATH + endpoint;

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
