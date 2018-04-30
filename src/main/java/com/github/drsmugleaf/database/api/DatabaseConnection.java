package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.env.Env;
import com.github.drsmugleaf.env.Keys;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 31/03/2018.
 */
class DatabaseConnection {

    @Nonnull
    private static final String DRIVER = "org.postgresql.Driver";

    @Nonnull
    static Connection initialize() {
        Connection connection = getConnection();

        if (connection == null) {
            Database.LOGGER.error("Failed to establish database connection");
            System.exit(1);
        }

        Database.LOGGER.info("Established database connection");
        return connection;
    }

    @Nullable
    private static Connection getConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            Database.LOGGER.error("Missing PostgreSQL JDBC Driver", e);
            System.exit(1);
        }

        String uri = Env.get(Keys.DATABASE_URL);
        if (uri == null) {
            Database.LOGGER.error("Database URL environment variable is null");
            System.exit(1);
        }

        Map<String, String> credentials = getCredentials(uri);
        String url = credentials.get("url");
        String username = credentials.get("username");
        String password = credentials.get("password");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            Database.LOGGER.error("Failed to establish a database connection");
            System.exit(1);
        }

        return connection;
    }

    @Nonnull
    private static Map<String, String> getCredentials(@Nonnull String uri) {
        Pattern pattern = Pattern.compile("^postgres://(.*):(.*)@(.*):(.*)/(.*)");
        Matcher matcher = pattern.matcher(uri);

        Map<String, String> credentials = new HashMap<>();

        if (matcher.matches()) {
            credentials.put("url", "jdbc:postgresql://" + matcher.group(3) + ":" + matcher.group(4) + "/" + matcher.group(5) + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
            credentials.put("username", matcher.group(1));
            credentials.put("password", matcher.group(2));
        }

        return credentials;
    }

}
