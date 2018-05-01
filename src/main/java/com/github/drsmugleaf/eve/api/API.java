package com.github.drsmugleaf.eve.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
class API {

    @Nonnull
    private static final String URL = "https://esi.tech.ccp.is/latest/";

    @Nonnull
    public static final Logger LOGGER = LoggerFactory.getLogger(API.class);

    @Nonnull
    static JsonElement getResponse(@Nonnull String endpoint, @Nonnull Map<String, String> properties) {
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
            throw new APIException("Error opening connection to API endpoint" + endpoint, e);
        }

        properties.forEach(connection::setRequestProperty);

        StringBuilder response = new StringBuilder();
        try {
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new APIException("Error getting response from connection to API endpoint" + endpoint, e);
        }

        return new JsonParser().parse(response.toString());
    }

    @Nonnull
    static JsonElement getResponse(@Nonnull String endpoint) {
        return getResponse(endpoint, new HashMap<>());
    }

}
