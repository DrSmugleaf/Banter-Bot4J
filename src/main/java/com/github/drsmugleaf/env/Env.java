package com.github.drsmugleaf.env;

import com.github.drsmugleaf.BanterBot4J;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by DrSmugleaf on 10/01/2018.
 */
class Env {

    @Nonnull
    static final Properties PROPERTIES = new Properties();

    static {
        try(InputStream input = new FileInputStream(".env")) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            BanterBot4J.LOGGER.warn("Error reading .env file", e);
        }
    }

}
