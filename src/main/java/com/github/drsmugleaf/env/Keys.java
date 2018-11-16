package com.github.drsmugleaf.env;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    TRIPWIRE_TEST_PASSWORD("");

    @NotNull
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

}
