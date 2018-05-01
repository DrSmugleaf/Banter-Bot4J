package com.github.drsmugleaf.eve.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 01/05/2018.
 */
class API {

    private static final String URL = "https://esi.tech.ccp.is/latest/";

    public static final Logger LOGGER = LoggerFactory.getLogger(API.class);

    static String getResponse(@Nonnull String endpoint, @Nonnull Map<String, String> properties) {
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

        String response;
        try {
            response = connection.getResponseMessage();
        } catch (IOException e) {
            throw new APIException("Error getting response message from connection to API endpoint" + endpoint, e);
        }

        return response;
    }

    public static String getResponse(String endpoint) {
        return getResponse(endpoint, new HashMap<>());
    }

}
