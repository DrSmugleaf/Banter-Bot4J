package com.github.drsmugleaf.env;

import com.github.drsmugleaf.BanterBot4J;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by DrSmugleaf on 10/01/2018.
 */
public class Env {

    @Nonnull
    private static final Properties env;

    static {
        Properties defaultProperties = new Properties();
        System.getenv().forEach(defaultProperties::setProperty);
        env = new Properties(defaultProperties);

        try(InputStream input = new FileInputStream(".env")) {
            env.load(input);
            input.close();
        } catch (IOException e) {
            BanterBot4J.LOGGER.warn("Error reading .env file", e);
        }
    }

    @Nullable
    public static String get(@Nonnull Keys key) {
        return env.getProperty(key.name());
    }

}
