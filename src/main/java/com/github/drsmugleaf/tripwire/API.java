package com.github.drsmugleaf.tripwire;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
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
    static final String REFRESH_URL = URL + "refresh.php";

    @Nonnull
    static final String USER_AGENT = "Banter Bot4J";

    @Nonnull
    private static final SessionManager SESSION_MANAGER = new SessionManager();

    public static Connection.Response getSignatures(@Nonnull String username, @Nonnull String password) {
        Map<String, String> cookies = SESSION_MANAGER.getCookies(username, password);

        try {
            return Jsoup.connect(REFRESH_URL)
                    .cookies(cookies)
                    .method(Connection.Method.POST)
                    .data("mode", "init")
                    .data("systemID", "30000142")
                    .referrer(REFRESH_URL)
                    .userAgent(USER_AGENT)
                    .ignoreContentType(true)
                    .execute();
        } catch (IOException e) {
            throw new LoginException("Error getting signatures in " + REFRESH_URL + " with username " + username);
        }
    }

}
