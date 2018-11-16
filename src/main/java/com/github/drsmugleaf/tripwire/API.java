package com.github.drsmugleaf.tripwire;

import com.github.drsmugleaf.tripwire.session.Session;
import com.github.drsmugleaf.tripwire.session.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;

/**
 * Created by DrSmugleaf on 15/05/2018.
 */
public class API {

    @NotNull
    public static final Logger LOGGER = LoggerFactory.getLogger(API.class);

    @NotNull
    public static final String URL = "https://tripwire.eve-apps.com/";

    @NotNull
    public static final String LOGIN_URL = URL + "login.php";

    @NotNull
    public static final String CONNECT_URL = URL + "?system=";

    @NotNull
    public static final String REFRESH_URL = URL + "refresh.php";

    @NotNull
    public static final String USER_AGENT = "Banter Bot4J";

    @NotNull
    private static final SessionManager SESSION_MANAGER = new SessionManager();

    @NotNull
    public static final Gson GSON = new GsonBuilder().setDateFormat("YYYY-MM-DD HH:mm:ss").create();

    @NotNull
    public static Connection.Response refresh(long id, @NotNull String username, @NotNull String password) {
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
            throw new RefreshException("Error getting signatures in " + REFRESH_URL + " with username " + username);
        }
    }

}
