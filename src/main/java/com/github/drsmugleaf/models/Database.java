package com.github.drsmugleaf.models;

import com.github.drsmugleaf.env.Env;
import com.github.drsmugleaf.env.Keys;
import com.github.drsmugleaf.BanterBot4J;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DrSmugleaf on 13/05/2017.
 */
public class Database {

    private static final String URI = Env.get(Keys.DATABASE_URL);
    private static final Map<String, String> CREDENTIALS = getCredentials(URI);
    private static final String URL = CREDENTIALS.get("url");
    private static final String USERNAME = CREDENTIALS.get("username");
    private static final String PASSWORD = CREDENTIALS.get("password");
    private static final String DRIVER = "org.postgresql.Driver";
    public static final Connection conn = init();

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch(ClassNotFoundException e) {
            BanterBot4J.LOGGER.error("Missing PostgreSQL JDBC Driver", e);
            System.exit(1);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Nullable
    public static Connection init() {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch(SQLException e) {
            BanterBot4J.LOGGER.error("Database connection failed", e);
        }

        if(connection != null) {
            BanterBot4J.LOGGER.info("Established database connection");
            return connection;
        } else {
            BanterBot4J.LOGGER.error("Failed to establish database connection");
        }

        return null;
    }

    private static Map<String, String> getCredentials(String uri) {
        Pattern pattern = Pattern.compile("^postgres://(.*):(.*)@(.*):(.*)/(.*)");
        Matcher matcher = pattern.matcher(uri);

        Map<String, String> credentials = new HashMap<>();

        if(matcher.matches()) {
            credentials.put("url", "jdbc:postgresql://" + matcher.group(3) + ":" + matcher.group(4) + "/" + matcher.group(5) + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
            credentials.put("username", matcher.group(1));
            credentials.put("password", matcher.group(2));
        }

        return credentials;
    }

}
