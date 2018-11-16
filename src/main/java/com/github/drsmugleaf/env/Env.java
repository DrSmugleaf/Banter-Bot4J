package com.github.drsmugleaf.env;

import com.github.drsmugleaf.BanterBot4J;

import org.jetbrains.annotations.NotNull;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by DrSmugleaf on 10/01/2018.
 */
class Env {

    @NotNull
    static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = new FileInputStream(".env")) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            BanterBot4J.LOGGER.warn("Error reading .env file", e);
        }
    }

}
