package com.github.drsmugbrain.util;

import com.google.api.client.util.ArrayMap;

import java.io.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Brian on 14/05/2017.
 */
public class Env {

    public static Map<String, String> readFile() {
        Map<String, String> data = new ArrayMap<>();
        Properties properties = new Properties();

        Map<String, String> env = System.getenv();
        for(String envName : env.keySet()) {
            data.put(envName, env.get(envName));
        }

        try(InputStream input = new FileInputStream(".env")) {
            properties.load(input);
            input.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        Enumeration<?> enumeration = properties.propertyNames();
        while(enumeration.hasMoreElements()) {
            String key = ((String) enumeration.nextElement()).trim();
            String value = properties.getProperty(key).trim();
            data.put(key, value);
        }

        return data;
    }

}
