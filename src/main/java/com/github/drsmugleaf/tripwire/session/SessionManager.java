package com.github.drsmugleaf.tripwire.session;

import com.github.drsmugleaf.tripwire.API;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 16/05/2018.
 */
public class SessionManager {

    @Nonnull
    private final Map<String, Session> SESSIONS = new HashMap<>();

    public SessionManager() {}

    @Nonnull
    private static Session login(long id, @Nonnull String username, @Nonnull String password) {
        try {
            Connection.Response loginResponse = Jsoup.connect(API.LOGIN_URL)
                    .method(Connection.Method.POST)
                    .data("username", username)
                    .data("password", password)
                    .data("mode", "login")
                    .referrer(API.LOGIN_URL)
                    .userAgent(API.USER_AGENT)
                    .execute();

            Map<String, String> cookies = loginResponse.cookies();
            Connection.Response connectResponse = Jsoup.connect(API.CONNECT_URL)
                    .cookies(cookies)
                    .method(Connection.Method.POST)
                    .referrer(API.CONNECT_URL)
                    .userAgent(API.USER_AGENT)
                    .execute();

            Document html = connectResponse.parse();
            String name = html.getElementById("user").text();
            String version = html.select("meta[name=version]").attr("content");
            return new Session(id, name, cookies, version);
        } catch (IOException e) {
            throw new LoginException("Error logging into " + API.LOGIN_URL + " with username " + username);
        }
    }

    @Nonnull
    public Session getSession(long id, @Nonnull String username, @Nonnull String password) {
        if (SESSIONS.containsKey(username)) {
            return SESSIONS.get(username);
        } else {
            Session session = login(id, username, password);
            SESSIONS.put(username, session);
            return session;
        }
    }

}
