package com.github.drsmugleaf.tripwire;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 16/05/2018.
 */
class SessionManager {

    @Nonnull
    private final Map<String, Map<String, String>> COOKIES = new HashMap<>();

    SessionManager() {}

    @Nonnull
    private static Map<String, String> login(@Nonnull String username, @Nonnull String password) {
        try {
            return Jsoup.connect(API.LOGIN_URL)
                    .method(Connection.Method.POST)
                    .data("username", username)
                    .data("password", password)
                    .data("mode", "login")
                    .referrer(API.LOGIN_URL)
                    .userAgent(API.USER_AGENT)
                    .execute()
                    .cookies();
        } catch (IOException e) {
            throw new LoginException("Error logging into " + API.LOGIN_URL + " with username " + username);
        }
    }

    @Nonnull
    Map<String, String> getCookies(@Nonnull String username, @Nonnull String password) {
        if (COOKIES.containsKey(username)) {
            return COOKIES.get(username);
        } else {
            Map<String, String> cookies = login(username, password);
            COOKIES.put(username, cookies);
            return cookies;
        }
    }

}
