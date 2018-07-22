package com.github.drsmugleaf.discord.oauth2;

import org.json.JSONArray;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by DrSmugleaf on 22/07/2018
 */
public class API {

    @Nullable
    static String clientID = null;

    @Nullable
    static String clientSecret = null;

    @Nonnull
    private static final String USER_AGENT = "DiscordBot (https://github.com/DrSmugleaf/Banter-Bot4J, 1.0)";

    @Nonnull
    private static final String API_VERSION = "v6";

    @Nonnull
    private static final String API_URL = "https://discordapp.com/api/" + API_VERSION + "/";

    public static void setClientID(@Nonnull String id) {
        clientID = id;
    }

    public static void setClientSecret(@Nonnull String secret) {
        clientSecret = secret;
    }

    @Nonnull
    static JSONArray request(@Nonnull Client client, @Nonnull String endpoint, @Nonnull String method) {
        URL url;
        try {
            url = new URL(API_URL + endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid endpoint", e);
        }

        HttpsURLConnection connection;
        try {
            connection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new APIException("Error opening connection with Discord's API", e);
        }

        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException e) {
            throw new IllegalArgumentException("Invalid request method " + method, e);
        }

        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("client_id", clientID);
        connection.setRequestProperty("Authorization", "Bearer " + client.CREDENTIALS.getAccessToken());

        InputStream stream;
        try {
            stream = connection.getInputStream();
        } catch (IOException e) {
            throw new APIException("Error getting response from Discord's API", e);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        StringBuilder content = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            throw new APIException("Error reading line from Discord's response", e);
        }

        return new JSONArray(content.toString());
    }

}
