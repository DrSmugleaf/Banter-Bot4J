package com.github.drsmugleaf.env;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by DrSmugleaf on 16/01/2018.
 */
public enum Keys {

    AZURE_TRANSLATOR_KEY,
    BOT_PREFIX("!"),
    DATABASE_URL,
    DISCORD_TOKEN,
    DISCORD_WARNING_CHANNEL(""),
    GOOGLE_KEY,
    TRIPWIRE_TEST_USERNAME(""),
    TRIPWIRE_TEST_PASSWORD(""),
    TAK_POLICY_DIRECTORY("");

    public final String VALUE;

    Keys(@Nullable String defaultValue) {
        String fileEnvProperty = Env.PROPERTIES.getProperty(name());
        String systemEnvProperty = System.getenv(name());

        if (fileEnvProperty != null && !fileEnvProperty.isEmpty()) {
            VALUE = fileEnvProperty;
        } else if (systemEnvProperty != null && !systemEnvProperty.isEmpty()) {
            VALUE = systemEnvProperty;
        } else if (defaultValue != null) {
            VALUE = defaultValue;
        } else {
            throw new InitializationException("No value has been set for environment variable " + this);
        }
    }

    Keys() {
        this(null);
    }

    private static class Env {

        private static final Properties PROPERTIES = getProperties();

        private static Properties getProperties() {
            Properties properties = new Properties();

            try (InputStream input = new FileInputStream(".env")) {
                properties.load(input);
            } catch (FileNotFoundException e) {
                BanterBot4J.LOGGER.warn("No .env file found");
            } catch (IOException e) {
                BanterBot4J.LOGGER.error("Error reading .env file", e);
            }

            return properties;
        }

    }

}
