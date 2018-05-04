package com.github.drsmugleaf.eve.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
class API {

    @Nonnull
    private static final String URL = "https://esi.tech.ccp.is/latest/";

    @Nonnull
    public static final Logger LOGGER = LoggerFactory.getLogger(API.class);

    @Nonnull
    private static final Cache<String, JsonElement> CACHE = new Cache<>();

    @Nonnull
    private static final SimpleDateFormat EXPIRES_FORMAT = new SimpleDateFormat("E, d MMM y H:m:s z", Locale.US);

    @Nonnull
    private static String getCacheKey(@Nonnull String endpoint, @Nonnull Map<String, String> properties) {
        String mapString = properties
                .entrySet()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining("&"));

        return endpoint + mapString;
    }

    @Nonnull
    static JsonElement getResponse(@Nonnull String endpoint, @Nonnull Map<String, String> properties, @Nonnull String method) {
        String cacheKey = getCacheKey(endpoint, properties);
        JsonElement cachedJson = CACHE.get(cacheKey);
        if (cachedJson != null) {
            return cachedJson;
        }

        URL url;
        try {
            url = new URL(URL + endpoint);
        } catch (MalformedURLException e) {
            throw new APIException("Error creating url instance", e);
        }

        HttpsURLConnection connection;
        try {
            connection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new APIException("Error opening connection to API endpoint " + endpoint, e);
        }

        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException e) {
            throw new APIException("Error setting request method to " + method, e);
        }

        properties.forEach(connection::setRequestProperty);

        StringBuilder response = new StringBuilder();
        try {
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new APIException("Error getting response from connection to API endpoint " + endpoint, e);
        }

        String expires = connection.getHeaderField("Expires");
        Date date;
        try {
            date = EXPIRES_FORMAT.parse(expires);
        } catch (ParseException e) {
            throw new APIException("Error parsing date from expires header: " + expires, e);
        }

        JsonElement parsedElement = new JsonParser().parse(response.toString());
        CACHE.set(cacheKey, parsedElement, date);
        return parsedElement;
    }

    @Nonnull
    static JsonElement getResponse(@Nonnull String endpoint, String method) {
        return getResponse(endpoint, new HashMap<>(), method);
    }

    @Nonnull
    static JsonElement getResponse(@Nonnull String endpoint) {
        return getResponse(endpoint, "GET");
    }

}
