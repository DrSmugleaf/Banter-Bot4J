package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.BanterBot4J;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;

import org.jetbrains.annotations.NotNull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 06/11/2018
 */
public abstract class API {

    @NotNull
    public static final String HOME_URL = "https://dennisreep.nl/dbd/";

    @NotNull
    public static final String KILLER_PERKS_URL = "https://dennisreep.nl/dbd/perks/killer/";

    @NotNull
    public static final String SURVIVOR_PERKS_URL = "https://dennisreep.nl/dbd/perks/survivor/";

    @NotNull
    public static final String KILLERS_URL = "https://dennisreep.nl/dbd/killers/";

    @NotNull
    public static final String KILLER_ROULETTE_URL = "https://dennisreep.nl/dbd/roulette/killer/";

    @NotNull
    public static final String SURVIVOR_ROULETTE_URL = "https://dennisreep.nl/dbd/roulette/survivor/";

    @NotNull
    private static final String IMAGES_PATH = Objects.requireNonNull(API.class.getClassLoader().getResource("deadbydaylight")).getFile();

    @NotNull
    private static final String PATH = "https://dennisreep.nl/dbd/api/v2/";

    @NotNull
    protected static final Gson GSON = new GsonBuilder().create();

    @NotNull
    public static InputStream getDBDLogo() {
        String fileName = "/logo.png";
        try {
            return new FileInputStream(IMAGES_PATH + fileName);
        } catch (FileNotFoundException e) {
            BanterBot4J.warn("DBD logo image not found", e);
            throw new IllegalStateException("DBD logo image not found", e);
        }
    }

    @NotNull
    protected static JsonObject getResponse(@NotNull final String endpoint) {
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
