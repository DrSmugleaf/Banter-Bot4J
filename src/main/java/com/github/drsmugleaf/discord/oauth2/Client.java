package com.github.drsmugleaf.discord.oauth2;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpContent;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by DrSmugleaf on 21/07/2018
 */
public class Client {

    @Nullable
    private static String clientID = null;

    @Nullable
    private static String clientSecret = null;

    @Nonnull
    private static final String API_VERSION = "v6";

    @Nonnull
    private static final String API_URL = "https://discordapp.com/api/" + API_VERSION + "/";

    @Nonnull
    private static final String TOKEN_URL = API_URL + "oauth2/token";

    @Nonnull
    private static final String GRANT_TYPE = "authorization_code";

    @Nonnull
    private static final String REDIRECT_URI = "https://www.discord.com";

    @Nonnull
    private final Credential CREDENTIALS;

    public Client(@Nonnull String code, @Nonnull Scope... scopes) {
        CREDENTIALS = getCredentials(code);
    }

    public static Credential getCredentials(@Nonnull String code, @Nonnull Scope... scopes) {
        if (clientID == null) {
            throw new IllegalStateException("Client id has not been set");
        } else if (clientSecret == null) {
            throw new IllegalStateException("Client secret has not been set");
        }

        List<String> scopeList = Stream.of(scopes).map(Scope::getName).collect(Collectors.toList());
        GoogleCredential credential = new GoogleCredential.Builder()
                .setJsonFactory(new JacksonFactory())
                .setTransport(new ApacheHttpTransport())
                .setClientSecrets(clientID, clientSecret)
                .build()
                .createScoped(scopeList);
        HttpRequestFactory requestFactory = new ApacheHttpTransport().createRequestFactory(credential);
        GenericUrl url = new GenericUrl(TOKEN_URL);
        url
                .set("client_id", clientID)
                .set("client_secret", clientSecret)
                .set("grant_type", GRANT_TYPE)
                .set("code", code)
                .set("redirect_uri", REDIRECT_URI);

        HttpResponse response;
        try {
            response = requestFactory.buildPostRequest(url, new MockHttpContent()).execute();
        } catch (IOException e) {
            throw new AuthenticationException("Error building post request to get an access token from discord", e);
        }

        JSONObject jsonResponse;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getContent()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            jsonResponse = new JSONObject(content.toString());
        } catch (IOException e) {
            throw new AuthenticationException("Error parsing json from discord's response to get an access token", e);
        }

        String token = jsonResponse.getString("access_token");
        Long expiresIn = jsonResponse.getLong("expires_in");
        String refreshToken = jsonResponse.getString("refresh_token");
        credential
                .setAccessToken(token)
                .setExpiresInSeconds(expiresIn)
                .setRefreshToken(refreshToken);

        return credential;
    }

    public static void setClientID(@Nonnull String id) {
        clientID = id;
    }

    public static void setClientSecret(@Nonnull String secret) {
        clientSecret = secret;
    }

    public String getToken() {
        return CREDENTIALS.getAccessToken();
    }

    public List<Guild> getGuilds() {
        String url = API_URL + "users/@me/guilds";
        HttpsURLConnection connection;
        try {
            connection = (HttpsURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            throw new APIException("Error getting current user's guilds", e);
        }

        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new IllegalStateException("Invalid protocol", e);
        }

        connection.setRequestProperty("User-Agent", "DiscordBot (https://github.com/DrSmugleaf/Banter-Bot4J, 1.0)");
        connection.setRequestProperty("client_id", clientID);
        connection.setRequestProperty("Authorization", "Bearer " + CREDENTIALS.getAccessToken());

        JSONArray response;
        try {
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            response = new JSONArray(content.toString());
        } catch (IOException e) {
            throw new APIException("Error getting user's current guilds", e);
        }

        List<Guild> guilds = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            JSONObject guildJson = response.getJSONObject(i);

            boolean isOwner = guildJson.getBoolean("owner");
            long permissions = guildJson.getLong("permissions");
            String icon = null;
            if (!guildJson.isNull("icon")) {
                icon = guildJson.getString("icon");
            }
            String name = guildJson.getString("name");
            long id = guildJson.getLong("id");

            Guild guild = new Guild(isOwner, permissions, icon, name, id);

            guilds.add(guild);
        }

        return guilds;
    }

}
