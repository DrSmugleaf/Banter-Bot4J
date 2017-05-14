package com.github.drsmugbrain;

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

    private static final String URI = EnvVariables.getEnvVariables().get("databaseURI");
    private static final Map<String, String> CREDENTIALS = getCredentials(URI);
    private static final String URL = CREDENTIALS.get("url");
    private static final String USERNAME = CREDENTIALS.get("username");
    private static final String PASSWORD = CREDENTIALS.get("password");
    private static final String DRIVER = "org.postgresql.Driver";

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch(ClassNotFoundException ex) {
            System.out.println("Missing PostgreSQL JDBC Driver");
            System.exit(1);
        }
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
    }

    public static void main() {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch(SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }

        if(connection != null) {
            System.out.println("Established database connection");
        } else {
            System.out.println("Failed to establish database connection");
        }
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
