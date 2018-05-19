package com.github.drsmugleaf.tripwire;

import com.google.gson.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 15/05/2018.
 */
public class API {

    @Nonnull
    static final Logger LOGGER = LoggerFactory.getLogger(API.class);

    @Nonnull
    static final String URL = "https://tripwire.eve-apps.com/";

    @Nonnull
    static final String LOGIN_URL = URL + "login.php";

    @Nonnull
    static final String CONNECT_URL = URL + "?system=";

    @Nonnull
    static final String REFRESH_URL = URL + "refresh.php";

    @Nonnull
    static final String USER_AGENT = "Banter Bot4J";

    @Nonnull
    private static final SessionManager SESSION_MANAGER = new SessionManager();

    @Nonnull
    private static final Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-DD HH:mm:ss").create();

    public static Connection.Response refresh(long id, @Nonnull String username, @Nonnull String password) {
        Session session = SESSION_MANAGER.getSession(id, username, password);

        try {
            return Jsoup.connect(REFRESH_URL)
                    .cookies(session.COOKIES)
                    .method(Connection.Method.POST)
                    .data("mode", "refresh")
                    .data("systemID", "30000142")
                    .data("systemName", "Jita")
                    .data("instance", session.INSTANCE)
                    .data("version", session.VERSION)
                    .referrer(REFRESH_URL)
                    .userAgent(USER_AGENT)
                    .ignoreContentType(true)
                    .execute();
        } catch (IOException e) {
            throw new LoginException("Error getting signatures in " + REFRESH_URL + " with username " + username);
        }
    }

    public static List<Signature> getSignatures(long id, @Nonnull String username, @Nonnull String password) {
        String json = refresh(id, username, password).body();
        JsonObject signatures = new JsonParser().parse(json).getAsJsonObject().getAsJsonObject("signatures");
        List<Signature> signatureList = new ArrayList<>();

        for (Map.Entry<String, JsonElement> signatureEntry : signatures.entrySet()) {
            Signature signature = gson.fromJson(signatureEntry.getValue(), Signature.class);
            signatureList.add(signature);
        }

        return signatureList;
    }

}
