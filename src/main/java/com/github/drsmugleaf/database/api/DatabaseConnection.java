package com.github.drsmugleaf.database.api;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.env.Keys;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 31/03/2018.
 */
class DatabaseConnection {

    private static final String DRIVER = "org.postgresql.Driver";

    static HikariDataSource initialize() {
        HikariDataSource connection = getDataSource();

        if (connection == null) {
            Database.LOGGER.error("Failed to establish a database connection");
            System.exit(1);
        }

        Database.LOGGER.info("Established a database connection");
        return connection;
    }

    @Nullable
    private static HikariDataSource getDataSource() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            Database.LOGGER.error("PostgreSQL JDBC Driver not found", e);
            System.exit(1);
        }

        Map<String, String> credentials = getCredentials(Keys.DATABASE_URL.VALUE);
        String url = credentials.get("url");
        String username = credentials.get("username");
        String password = credentials.get("password");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        HikariDataSource dataSource = new HikariDataSource(config);
        try (Connection ignored = dataSource.getConnection()) {
            return dataSource;
        } catch (SQLException e) {
            Database.LOGGER.error("Failed to establish a database connection");
            System.exit(1);
            return null;
        }
    }

    private static Map<String, String> getCredentials(String uri) {
        Pattern pattern = Pattern.compile("^postgres://(.*):(.*)@(.*):(.*)/(.*)");
        Matcher matcher = pattern.matcher(uri);

        Map<String, String> credentials = new HashMap<>();

        if (matcher.matches()) {
            credentials.put("url", "jdbc:postgresql://" + matcher.group(3) + ":" + matcher.group(4) + "/" + matcher.group(5) + "?sslmode=require");
            credentials.put("username", matcher.group(1));
            credentials.put("password", matcher.group(2));
        }

        return credentials;
    }

}
