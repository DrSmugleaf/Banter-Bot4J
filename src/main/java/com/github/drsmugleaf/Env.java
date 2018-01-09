package com.github.drsmugleaf;

import com.github.drsmugleaf.util.Bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by DrSmugleaf on 10/01/2018.
 */
public class Env {

    public static Map<String, String> env = new HashMap<>(System.getenv());

    static {
        try(InputStream input = new FileInputStream(".env")) {
            Properties properties = new Properties();
            properties.load(input);
            for (String s : properties.stringPropertyNames()) {
                env.put(s, properties.getProperty(s));
            }
            input.close();
        } catch (IOException e) {
            Bot.LOGGER.error("Error reading .env file", e);
        }
    }

    public static String get(String key) {
        return env.get(key);
    }

}
